package com.ssafy.springbootdeveloper.blog.controller;

import com.ssafy.springbootdeveloper.blog.domain.Article;
import com.ssafy.springbootdeveloper.blog.dto.AddArticleRequest;
import com.ssafy.springbootdeveloper.blog.dto.ArticleListViewResponse;
import com.ssafy.springbootdeveloper.blog.dto.ArticleViewResponse;
import com.ssafy.springbootdeveloper.blog.dto.UpdateArticleRequest;
import com.ssafy.springbootdeveloper.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/articles")
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping()
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article saveArticle = blogService.save(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(saveArticle);
    }

    @GetMapping()
    public ResponseEntity<List<ArticleListViewResponse>> findAllArticles(){
        List<ArticleListViewResponse> articles = blogService.findAll()
            .stream()
            .map(ArticleListViewResponse::new)
            .toList();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleViewResponse> findArticle(@PathVariable("id") long id){
        Article article = blogService.findById(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ArticleViewResponse(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle = blogService.update(id,request);

        return ResponseEntity.ok().body(updatedArticle);
    }
}
