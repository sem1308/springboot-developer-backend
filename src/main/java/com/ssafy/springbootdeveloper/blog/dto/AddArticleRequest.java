package com.ssafy.springbootdeveloper.blog.dto;

import com.ssafy.springbootdeveloper.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity(String author){
        return Article.builder()
            .title(title)
            .content(content)
            .author(author)
            .build();
    }
}
