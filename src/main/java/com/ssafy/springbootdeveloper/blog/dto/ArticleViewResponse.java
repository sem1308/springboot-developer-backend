package com.ssafy.springbootdeveloper.blog.dto;

import com.ssafy.springbootdeveloper.blog.domain.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleViewResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime updatedDateTime;

    private String author;

    public ArticleViewResponse(Article article) {
        this.id = article.getId();;
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdDateTime = article.getCreatedDateTime();
        this.updatedDateTime = article.getUpdatedDateTime();
        this.author = article.getAuthor();
    }
}