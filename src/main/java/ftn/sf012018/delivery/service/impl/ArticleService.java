package ftn.sf012018.delivery.service.impl;

import ftn.sf012018.delivery.lucene.indexing.handlers.*;
import ftn.sf012018.delivery.lucene.search.QueryBuilderCustom;
import ftn.sf012018.delivery.lucene.search.SearchQueryGenerator;
import ftn.sf012018.delivery.lucene.search.SimpleQueryElasticsearch;
import ftn.sf012018.delivery.mapper.ArticleMapper;
import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.ArticleDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.Article;
import ftn.sf012018.delivery.model.mappings.user.Store;
import ftn.sf012018.delivery.model.query.ArticleQueryOptions;
import ftn.sf012018.delivery.repository.ArticleRepository;
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

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void index(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void reindex() {
        File dataDir = getResourceFilePath(dataFilesPath);
        indexUnitFromFile(dataDir);
    }

    @Override
    public void delete(ArticleDTO articleDTO) {
        articleRepository.delete(articleMapper.mapModel(articleDTO));
    }

    @Override
    public void update(ArticleDTO articleDTO, ArticleDTO articleDTOOld) throws IOException {
        String articleName = articleDTOOld.getName();
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "articles", articleName))
                .build();

        SearchHits<Article> articles =
                elasticsearchRestTemplate.search(searchQuery, Article.class, IndexCoordinates.of("articles"));
        Article article = articles.getSearchHit(0).getContent();

        indexUploadedFile(articleDTO);
    }

    @Override
    public Set<ArticleDTO> getByStore(StoreDTO storeDTO) {
        Page<Article> articles = articleRepository.findByStore(storeMapper.mapModel(storeDTO), Pageable.unpaged());

        return articleMapper.mapToDTO(articles.toSet());
    }


    @Override
    public Set<ArticleDTO> getByStoreAndCustomQuery(ArticleQueryOptions articleQueryOptions) {
        QueryBuilder nameQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("name", articleQueryOptions.getName()));
        QueryBuilder descriptionQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("description", articleQueryOptions.getDescription()));
        QueryBuilder priceRangeQuery = SearchQueryGenerator.createRangeQueryBuilder(
                new SimpleQueryElasticsearch("price", String.valueOf(articleQueryOptions.getPriceStart())+"-"+
                        String.valueOf(articleQueryOptions.getPriceEnd())));
        //TO:DO implementirati za opseg ocene i obseg broja komentara artikla i po prodavcu

        BoolQueryBuilder boolQueryBuilder = QueryBuilders
                .boolQuery()
                .should(nameQuery)
                .should(descriptionQuery)
                .should(priceRangeQuery);

        return searchByBoolQuery(boolQueryBuilder).map( articles -> articleMapper.mapToDTO(articles.getContent())).toSet();
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
    public void indexUploadedFile(ArticleDTO articleDTO) throws IOException {
        String fileName = saveUploadedFileInFolder(articleDTO.getDescription());
        if(fileName != null){
            Article articleIndexUnit = getHandler(fileName).getIndexUnit(new File(fileName));
            articleIndexUnit.setName(articleDTO.getName());
            articleIndexUnit.setImage(articleDTO.getImage());
            articleIndexUnit.setPrice(articleDTO.getPrice());
            articleIndexUnit.setStore(storeMapper.mapModel(articleDTO.getStoreDTO()));

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
            Path path = Paths.get(getResourceFilePath(dataFilesPath).getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            Path filepath = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            Files.write(filepath, bytes);
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
