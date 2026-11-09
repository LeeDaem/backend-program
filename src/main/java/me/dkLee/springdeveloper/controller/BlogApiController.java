package me.dkLee.springdeveloper.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.dkLee.springdeveloper.dao.Article;
import me.dkLee.springdeveloper.dto.ArticleCreationRequest;
import me.dkLee.springdeveloper.dto.ArticleResponse;
import me.dkLee.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleCreationRequest articleCreationRequest) {
        // 서비스에게 블로그 글 등록읠 의뢰
        Article savedArticle = blogService.save(articleCreationRequest);
        // 성공했다면 ResponseEntity 객체를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    // 블로그 글 전체 조회
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        /*
        List<Article> articleList = blogService.findAll();
        List<ArticleResponse> articleResponsesList = new ArrayList<>();
        for (Article article : articleList) {
            articleResponsesList.add(new ArticleResponse(article));
        }
        */
        List<ArticleResponse> articleResponsesList = blogService.findAll().stream().map(ArticleResponse::new).toList();
        return ResponseEntity.ok().body(articleResponsesList);
    }
    // 특정 블로그 글 조회.
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable Long id) throws IllegalAccessException {
        Article article = blogService.getById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    // 특정 블로그 글 삭제.
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
