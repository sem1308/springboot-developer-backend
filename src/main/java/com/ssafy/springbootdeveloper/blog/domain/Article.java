package com.ssafy.springbootdeveloper.blog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //=== 비즈니스 로직 ===//
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedDateTime = LocalDateTime.now();
    }
}
