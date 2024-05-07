package com.ssafy.springbootdeveloper.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootdeveloper.config.jwt.JwtFactory;
import com.ssafy.springbootdeveloper.config.jwt.JwtProperties;
import com.ssafy.springbootdeveloper.jwt.domain.RefreshToken;
import com.ssafy.springbootdeveloper.jwt.dto.CreateAccessTokenRequest;
import com.ssafy.springbootdeveloper.jwt.repository.RefreshTokenRespository;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TokenApiControllerTest {
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
        final String url="/api/token";

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
        ResultActions resultActions=mockMvc.perform(
            MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody));

        // then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty());
    }
}