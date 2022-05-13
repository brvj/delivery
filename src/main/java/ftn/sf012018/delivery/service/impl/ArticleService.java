package ftn.sf012018.delivery.service.impl;

import ftn.sf012018.delivery.lucene.indexing.handlers.*;
import ftn.sf012018.delivery.lucene.search.QueryBuilderCustom;
import ftn.sf012018.delivery.lucene.search.SearchQueryGenerator;
import ftn.sf012018.delivery.lucene.search.SimpleQueryElasticsearch;
import ftn.sf012018.delivery.mapper.ArticleMapper;
import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.ArticleRequestDTO;
import ftn.sf012018.delivery.model.dto.ArticleResponseDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.Article;
import ftn.sf012018.delivery.model.query.ArticleQueryOptions;
import ftn.sf012018.delivery.repository.ArticleRepository;
import ftn.sf012018.delivery.repository.user.StoreRepository;
import ftn.sf012018.delivery.security.annotations.AuthorizeAdminOrStore;
import ftn.sf012018.delivery.security.annotations.AuthorizeAny;
import ftn.sf012018.delivery.service.IArticleService;
import ftn.sf012018.delivery.util.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;

@Service
public class ArticleService implements IArticleService {
    @Value("${files.path}")
    private String dataFilesPath;

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    private static String folder = "";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    @AuthorizeAdminOrStore
    public void index(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void reindex() {
        File dataDir = getResourceFilePath(dataFilesPath);
        indexUnitFromFile(dataDir);
    }

    @Override
    @AuthorizeAdminOrStore
    public void delete(ArticleRequestDTO articleDTO) throws IOException {
        articleRepository.delete(articleMapper.mapModel(articleDTO));
    }

    @Override
    @AuthorizeAdminOrStore
    public void update(ArticleRequestDTO articleDTO) throws IOException {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "_id", articleDTO.getId()))
                .build();

        SearchHits<Article> articles =
                elasticsearchRestTemplate.search(searchQuery, Article.class, IndexCoordinates.of("articles"));
        Article article = articles.getSearchHit(0).getContent();

        indexUploadedFile(articleDTO);
    }

    @Override
    @AuthorizeAny
    public Set<ArticleResponseDTO> getByStore(StoreDTO storeDTO, Pageable pageable) {
        Page<Article> articles = articleRepository.findByStore(storeMapper.mapModel(storeDTO), pageable);

        return articleMapper.mapToDTO(articles.toSet());
    }

    @Override
    @AuthorizeAny
    public Set<ArticleResponseDTO> getByStoreAndCustomQuery(ArticleQueryOptions articleQueryOptions) {
        QueryBuilder nameQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("name", articleQueryOptions.getName()));
        QueryBuilder descriptionQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("description", articleQueryOptions.getDescription()));
        QueryBuilder priceRangeQuery = SearchQueryGenerator.createRangeQueryBuilder(
                new SimpleQueryElasticsearch("price", String.valueOf(articleQueryOptions.getPriceStart())+"-"+
                        String.valueOf(articleQueryOptions.getPriceEnd())));
        //TODO implementirati za opseg ocene i obseg broja komentara artikla i po prodavcu

        BoolQueryBuilder boolQueryBuilder = QueryBuilders
                .boolQuery()
                .should(nameQuery)
                .should(descriptionQuery)
                .should(priceRangeQuery);

        return searchByBoolQuery(boolQueryBuilder).map(articles -> articleMapper.mapToDTO(articles.getContent())).toSet();
    }

    @Override
    public int indexUnitFromFile(File file) {
        DocumentHandler handler;
        String fileName;
        int retVal = 0;
        try {
            File[] files;
            if(file.isDirectory()){
                files = file.listFiles();
            }else{
                files = new File[1];
                files[0] = file;
            }
            assert files != null;
            for(File newFile : files){
                if(newFile.isFile()){
                    fileName = newFile.getName();
                    handler = getHandler(fileName);
                    if(handler == null){
                        System.out.println("Nije moguce indeksirati dokument sa nazivom: " + fileName);
                        continue;
                    }
                    index(handler.getIndexUnit(newFile));
                    retVal++;
                } else if (newFile.isDirectory()){
                    retVal += indexUnitFromFile(newFile);
                }
            }
            System.out.println("indexing done");
        } catch (Exception e) {
            System.out.println("indexing NOT done");
        }
        return retVal;
    }

    @Override
    public void indexUploadedFile(ArticleRequestDTO articleDTO) throws IOException {
        String fileName = saveUploadedFileInFolder(articleDTO.getDescription());
        String imagePath = saveUploadedImageInFolder(articleDTO.getImage());
        if(fileName != null){
            Article articleIndexUnit = getHandler(fileName).getIndexUnit(new File(fileName));
            articleIndexUnit.setName(articleDTO.getName());
            articleIndexUnit.setImagePath(imagePath);
            articleIndexUnit.setPrice(articleDTO.getPrice());
            articleIndexUnit.setStore(storeRepository.findById(articleDTO.getStoreDTO().getId()).get());

            index(articleIndexUnit);
        }
    }

    @Override
    public File getResourceFilePath(String path) {
        return null;
    }

    public DocumentHandler getHandler(String fileName){
        return getDocumentHandler(fileName);
    }

    public static DocumentHandler getDocumentHandler(String fileName) {
        if(fileName.endsWith(".txt")){
            return new TextDocHandler();
        }else if(fileName.endsWith(".pdf")){
            return new PDFHandler();
        }else if(fileName.endsWith(".doc")){
            return new WordHandler();
        }else if(fileName.endsWith(".docx")){
            return new Word2007Handler();
        }else{
            return null;
        }
    }

    private String saveUploadedFileInFolder(MultipartFile file) throws IOException {
        String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("C:\\Users\\Boris\\Desktop\\REPO\\delivery\\src\\main\\resources\\files" + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            Path filepath = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            Files.write(filepath, bytes);
            retVal = path.toString();
        }
        return retVal;
    }

    @PostConstruct
    private void imagesPath() {
        String path = "src/main/resources/images/";

        File file = new File(path);
        folder = file.getAbsolutePath();
    }

    private String saveUploadedImageInFolder(MultipartFile file) throws IOException {
        String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }

    private SearchHits<Article> searchByBoolQuery(BoolQueryBuilder boolQueryBuilder) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        return elasticsearchRestTemplate.search(searchQuery, Article.class,  IndexCoordinates.of("articles"));
    }
}
