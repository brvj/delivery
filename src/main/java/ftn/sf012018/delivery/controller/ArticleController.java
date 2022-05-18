package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.ArticleRequestDTO;
import ftn.sf012018.delivery.model.dto.ArticleResponseDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.query.ArticleQueryOptions;
import ftn.sf012018.delivery.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Void> multiUploadFileModel(@ModelAttribute ArticleRequestDTO uploadModel) throws IOException {
        try {
            articleService.indexUploadedFile(uploadModel);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/index", consumes = { "multipart/form-data" })
    public ResponseEntity<String> update(@ModelAttribute ArticleRequestDTO uploadModel) throws IOException {
        try {
            articleService.update(uploadModel);

            return new ResponseEntity<>(uploadModel.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<String> deleteArticle(@ModelAttribute ArticleRequestDTO articleDTO){
        try {
            articleService.delete(articleDTO);

            return new ResponseEntity<>(articleDTO.getId(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Page<ArticleResponseDTO>> getAllByStore(@RequestBody StoreDTO storeDTO, Pageable pageable){
        try {
            return new ResponseEntity<>(articleService.getByStore(storeDTO, pageable), HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/query", produces = "application/json")
    public ResponseEntity<Set<ArticleResponseDTO>> getByCustomQuery(@RequestBody ArticleQueryOptions articleQueryOptions){
        try {
            return new ResponseEntity<>(articleService.getByStoreAndCustomQuery(articleQueryOptions), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ArticleResponseDTO> getById(@PathVariable("id") String id){
        ArticleResponseDTO response = articleService.getById(id);

        if (response != null) return new ResponseEntity<>(response, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
