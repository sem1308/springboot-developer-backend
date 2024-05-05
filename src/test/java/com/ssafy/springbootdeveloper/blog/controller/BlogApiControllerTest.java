package com.ssafy.springbootdeveloper.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootdeveloper.blog.domain.Article;
import com.ssafy.springbootdeveloper.blog.dto.AddArticleRequest;
import com.ssafy.springbootdeveloper.blog.dto.UpdateArticleRequest;
import com.ssafy.springbootdeveloper.blog.repository.BlogRepository;
import com.ssafy.springbootdeveloper.blog.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    // 직렬화, 역직렬화를 위한 클래스
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogService blogService;

    // test코드 공통으로 사용될 픽스처
    Article article;

    @BeforeEach
    public void mockMvcSetUp(){
        blogRepository.deleteAll();

        final String title = "title";
        final String content = "content";
        article = Article.builder()
            .title(title)
            .content(content)
            .build();
    }

    @DisplayName("addArticle : 블로그에 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        //given : 블로그 글 추가에 필요한 요청 객체 생성
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        AddArticleRequest request = new AddArticleRequest(title,content);

        final String requestBody = objectMapper.writeValueAsString(request);

        //when : 블로그 글 추가 API에 요청 보냄.
        ResultActions result = mockMvc.perform(
            MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );

        //then : 응답 코드가 201 Created인지 확인
        result.andExpect(
            MockMvcResultMatchers
                .status()
                .isCreated()
        );

        List<Article> articles = blogService.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles : 블로그의 모든 게시글을 가져오는 데 성공한다.")
    @Test
    public void findAllArticles() throws Exception{
        //given : 블로그 모든 게시글 조회 url을 저장한다, 새로운 게시글을 등록한다.
        final String url = "/api/articles";
        blogRepository.save(article);

        //when : 블로그 모든 게시글 조회 api를 요청한다.
        final ResultActions result = mockMvc.perform(
            MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then : 응답 코드가 200(OK) 인지 확인하고 전체 블로그 글 개수가 3개인지 확인

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value(article.getContent()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(article.getTitle()));
    }

    @DisplayName("findArticle : 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        Article savedArticle = blogRepository.save(article);

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders
                .get(url, savedArticle.getId())
        );

        //then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(article.getContent()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(article.getTitle()));
    }

    @DisplayName("deleteArticle : 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        Article savedArticle = blogRepository.save(article);

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders
                .delete(url, savedArticle.getId())
        );

        //then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk());

        List<Article> articles = blogService.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle : 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        Article savedArticle = blogRepository.save(article);

        final String updatedTitle = "updated title";
        final String updatedContent = "updated content";
        UpdateArticleRequest request = new UpdateArticleRequest(updatedTitle,updatedContent);

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders
                .patch(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk());

        Article updatedArticle = blogService.findById(savedArticle.getId());

        assertThat(updatedArticle.getTitle()).isEqualTo(updatedTitle);
        assertThat(updatedArticle.getContent()).isEqualTo(updatedContent);
    }
}