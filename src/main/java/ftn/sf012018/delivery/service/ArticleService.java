package ftn.sf012018.delivery.service;

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
import ftn.sf012018.delivery.contract.repository.ArticleRepository;
import ftn.sf012018.delivery.contract.repository.user.StoreRepository;
import ftn.sf012018.delivery.security.annotations.AuthorizeAdminOrStore;
import ftn.sf012018.delivery.security.annotations.AuthorizeAny;
import ftn.sf012018.delivery.contract.service.IArticleService;
import ftn.sf012018.delivery.util.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

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
        articleRepository.deleteById(articleDTO.getId());
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
    public Page<ArticleResponseDTO> getByStore(StoreDTO storeDTO, Pageable pageable) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "store.id", storeDTO.getId()))
                .build();

        SearchHits<Article> articles =
                elasticsearchRestTemplate.search(searchQuery, Article.class, IndexCoordinates.of("articles"));
        Set<ArticleResponseDTO> articlesSet = articles.map(articlesFunc -> articleMapper.mapToDTO(articlesFunc.getContent())).toSet();

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), articlesSet.size());
        final Page<ArticleResponseDTO> page = new PageImpl<>(articlesSet.stream().toList().subList(start, end),pageable, articlesSet.size());

        return page;
    }

    @Override
    @AuthorizeAny
    public Set<ArticleResponseDTO> getByStoreAndCustomQuery(ArticleQueryOptions articleQueryOptions) {
        QueryBuilder storeQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("_store.id", articleQueryOptions.getStoreDTO().getId()));
        QueryBuilder nameQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("_name", articleQueryOptions.getName()));
        QueryBuilder descriptionQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("_description", articleQueryOptions.getDescription()));
        QueryBuilder priceRangeQuery = SearchQueryGenerator.createRangeQueryBuilder(
                new SimpleQueryElasticsearch("_price", String.valueOf(articleQueryOptions.getPriceStart())+"-"+
                        String.valueOf(articleQueryOptions.getPriceEnd())));
        //TODO implementirati za opseg ocene i obseg broja komentara artikla i po prodavcu

        BoolQueryBuilder boolQueryBuilder = QueryBuilders
                .boolQuery()
                .should(storeQuery)
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
        // saveUploadedImageInFolder(articleDTO.getImage());
        if(fileName != null){
            Article articleIndexUnit = getHandler(fileName).getIndexUnit(new File(fileName));
            articleIndexUnit.setName(articleDTO.getName());
            articleIndexUnit.setImagePath(articleDTO.getImage().getOriginalFilename());
            articleIndexUnit.setPrice(articleDTO.getPrice());
            articleIndexUnit.setStore(storeRepository.findById(articleDTO.getStoreDTO().getId()).get());

            index(articleIndexUnit);
        }
    }

    @Override
    public File getResourceFilePath(String path) {
        return null;
    }

    @Override
    public ArticleResponseDTO getById(String id) {
        Article article = articleRepository.findById(id).get();

        if(article != null) return articleMapper.mapToDTO(article);

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
            Path path = Paths.get("C:\\Users\\Boris\\Desktop\\REPO\\delivery\\src\\main\\resources\\files"
                    + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            Path filepath = Paths.get(uploadDir + File.separator +
                    StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            Files.write(filepath, bytes);
            retVal = path.toString();
        }
        return retVal;
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

    public byte[] getArticleImage(String articleId) throws IOException {
        ArticleResponseDTO article = getById(articleId);
        File imgPath = new File("src/main/resources/images/" + article.getImage());
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return data.getData();
    }
}
