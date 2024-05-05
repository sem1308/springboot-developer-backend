package com.ssafy.springbootdeveloper.blog.dto;

import com.ssafy.springbootdeveloper.blog.domain.Article;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ArticleListViewResponse {
    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
