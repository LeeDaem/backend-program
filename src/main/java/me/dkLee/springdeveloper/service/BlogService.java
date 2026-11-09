package me.dkLee.springdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.dkLee.springdeveloper.dao.Article;
import me.dkLee.springdeveloper.dto.ArticleCreationRequest;
import me.dkLee.springdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(ArticleCreationRequest articleCreationRequest) {
        return blogRepository.save(articleCreationRequest.toEntity());
    }

    // 아티클 테이블에 있는 모든 메서드를 리턴해주는
    public List<Article> findAll() {
        return blogRepository.findAll();
    }


    // 특정 블로그 글 조회
    public Article getById(Long id) throws IllegalAccessException {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalAccessException("not found: " + id));
    }

    // 특정 블로그 글 삭제
    public void deleteById(long id) {
        blogRepository.deleteById(id);
    }
}
