package com.ssafy.springbootdeveloper.auth.service;

import com.ssafy.springbootdeveloper.config.jwt.TokenProvider;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import com.ssafy.springbootdeveloper.user.service.UserService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class TokenServiceImplTest {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    @Autowired
    TokenProvider tokenProvider;

    @DisplayName("createRefreshToken : 리프레시 토큰 생성에 성공합니다.")
    @Test
    void createRefreshToken() {
        // given
        String email = "test@ssafy.com";
        String password = "123456";

        Long id = userService.save(new AddUserRequest(email, password));
        User user = userService.findById(id);

        // when
        String token = tokenService.createRefreshToken(user);

        // then
        Long tokenUserId = tokenProvider.getUserId(token);

        Assertions.assertThat(tokenUserId).isEqualTo(id);
    }
}