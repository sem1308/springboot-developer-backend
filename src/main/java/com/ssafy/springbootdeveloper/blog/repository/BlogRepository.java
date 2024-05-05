package com.ssafy.springbootdeveloper.blog.repository;

import com.ssafy.springbootdeveloper.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Article,Long> {
}
