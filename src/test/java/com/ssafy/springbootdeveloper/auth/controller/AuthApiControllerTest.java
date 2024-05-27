package com.ssafy.springbootdeveloper.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootdeveloper.config.jwt.JwtFactory;
import com.ssafy.springbootdeveloper.config.jwt.JwtProperties;
import com.ssafy.springbootdeveloper.auth.domain.RefreshToken;
import com.ssafy.springbootdeveloper.auth.dto.CreateAccessTokenRequest;
import com.ssafy.springbootdeveloper.auth.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.auth.repository.RefreshTokenRespository;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import com.ssafy.springbootdeveloper.user.repository.UserRepository;
import com.ssafy.springbootdeveloper.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    RefreshTokenRespository refreshTokenRespository;


    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        userRepository.deleteAll();
    }

    @DisplayName("createNewAccessToken: 새로운 액세스 토큰을 발급한다.")
    @Test
    public void createNewAccessToken() throws Exception{
        // given
        final String url="/api/token/refresh";

        User testUser=userRepository.save(User.builder()
            .email("user@gmail.com")
            .password("test")
            .build());

        String refreshToken= JwtFactory.builder()
            .claims(Map.of("id", testUser.getId()))
            .build()
            .createToken(jwtProperties);

        refreshTokenRespository.save(new RefreshToken(testUser.getId(), refreshToken));

        CreateAccessTokenRequest request=new CreateAccessTokenRequest();
        request.setRefreshToken(refreshToken);

        final String requestBody=objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
            post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody));

        // then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

    @DisplayName("login_success: 이메일과 비밀번호로 토큰 발급에 성공한다.")
    @Test
    public void login_success() throws Exception{
        // given
        String url="/api/token/login";
        String email = "user@gmail.com";
        String password = "test";
        // 암호화를 위해 userService의 save 사용
        userService.save(new AddUserRequest(email,password));

        // token request 생성
        CreateTokenRequest request=new CreateTokenRequest(email,password);

        String requestBody=objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

    @DisplayName("login_failed: 이메일과 비밀번호로 토큰 발급에 실패한다.")
    @Test
    public void login_failed() throws Exception{
        // given
        String url="/api/token/login";
        String email = "user@gmail.com";
        String password = "test";
        // 암호화를 위해 userService의 save 사용
        userService.save(new AddUserRequest(email,password));

        // token request 생성
        String loginPassword = "testt";
        CreateTokenRequest request=new CreateTokenRequest(email,loginPassword);

        String requestBody=objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        resultActions
                .andExpect(status().isInternalServerError());
    }
}