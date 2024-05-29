package com.ssafy.springbootdeveloper.blog.service;

import com.ssafy.springbootdeveloper.blog.domain.Article;
import com.ssafy.springbootdeveloper.blog.dto.AddArticleRequest;
import com.ssafy.springbootdeveloper.blog.dto.UpdateArticleRequest;
import com.ssafy.springbootdeveloper.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    @Override
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Article findById(long id) {
        return blogRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("not found: " + id));
    }

    @Override
    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = findById(id);

        article.update(request.getTitle(),request.getContent());

        return article;
    }
}
