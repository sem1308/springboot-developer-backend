package com.ssafy.springbootdeveloper.blog.service;

import com.ssafy.springbootdeveloper.blog.domain.Article;
import com.ssafy.springbootdeveloper.blog.dto.AddArticleRequest;
import com.ssafy.springbootdeveloper.blog.dto.UpdateArticleRequest;

import java.util.List;

public interface BlogService {
    Article save(AddArticleRequest request, String userName);
    List<Article> findAll();
    Article findById(long id);
    void delete(long id);
    public Article update(long id, UpdateArticleRequest request);
}
