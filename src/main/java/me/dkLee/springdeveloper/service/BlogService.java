package me.dkLee.springdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.dkLee.springdeveloper.dao.Article;
import me.dkLee.springdeveloper.dto.ArticleCreationRequest;
import me.dkLee.springdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(ArticleCreationRequest articleCreationRequest) {
        return blogRepository.save(articleCreationRequest.toEntity());
    }

}
