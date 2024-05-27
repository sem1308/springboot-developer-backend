package com.ssafy.springbootdeveloper.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootdeveloper.auth.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.auth.service.TokenService;
import com.ssafy.springbootdeveloper.filter.TokenAuthenticationFilter;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    // 직렬화, 역직렬화를 위한 클래스
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    // test코드 공통으로 사용될 픽스처
    User user;

    @BeforeEach
    public void mockMvcSetUp(){
        userRepository.deleteAll();

        String email = "user123@gmail.com";
        String password = "user12345";

        AddUserRequest request = AddUserRequest.builder()
                .email(email)
                .password(password)
                .build();

        long id = userService.save(request);
        user = userService.findById(id);
    }

    @DisplayName("signUp : 새로운 사용자 추가에 성공한다.")
    @Test
    public void signUp() throws Exception {
        //given : 사용자 추가에 필요한 요청 객체 생성
        final String url = "/api/users";
        final String email = "user789@gmail.com";
        final String password = "user7890";

        AddUserRequest request = new AddUserRequest(email,password);

        final String requestBody = objectMapper.writeValueAsString(request);

        //when : 사용자 추가 API에 요청 보냄.
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );

        //then : 응답 코드가 201 Created인지 확인
        result.andExpect(
            status().isCreated()
        );

        List<User> users = userService.findAll();

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(1).getEmail()).isEqualTo(email);
        assertThat(passwordEncoder.matches(password,users.get(1).getPassword())).isEqualTo(true);
    }

    @DisplayName("getUser : 로그인한 사용자 조회에 성공한다.")
    @Test
    public void getUser() throws Exception {
        //given - 토큰 생성하고 사용자 조회 요청하기
        final String url = "/api/users";

        // 토큰 생성하기
        String accessToken = tokenService.createAccessToken(user);

        //when
        ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header(TokenAuthenticationFilter.HEADER_AUTHORIZATION,TokenAuthenticationFilter.TOKEN_PREFIX+accessToken));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.id").value(user.getId()));
    }
}