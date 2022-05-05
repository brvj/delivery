package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.ArticleDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.query.ArticleQueryOptions;
import ftn.sf012018.delivery.service.impl.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping(path = "/index", consumes = { "multipart/form-data" })
    public ResponseEntity<Void> multiUploadFileModel(@ModelAttribute ArticleDTO uploadModel) throws IOException {
        try {
            articleService.indexUploadedFile(uploadModel);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> deleteArticle(@RequestBody ArticleDTO articleDTO){
        try {
            articleService.delete(articleDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Set<ArticleDTO>> getAllByStore(@RequestBody StoreDTO storeDTO, Pageable pageable){
        try {
            return new ResponseEntity<>(articleService.getByStore(storeDTO, pageable), HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Set<ArticleDTO>> getByCustomQuery(@RequestBody ArticleQueryOptions articleQueryOptions){
        try {
            return new ResponseEntity<>(articleService.getByStoreAndCustomQuery(articleQueryOptions), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
