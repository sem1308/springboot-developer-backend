package com.ssafy.springbootdeveloper.user.service;

import com.ssafy.springbootdeveloper.jwt.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class UserServiceImplTest {
    @Autowired UserService userService;

    @DisplayName("findByTokenRequest : CreateTokenRequest로 유저 찾기에 성공한다.")
    @Test
    void findByTokenRequest() {
        String email = "test@test.com";
        String password = "test";
        CreateTokenRequest request = new CreateTokenRequest();
        request.setEmail(email);
        request.setPassword(password);

        Long userId = userService.save(new AddUserRequest(email, password));

        User user = userService.findByTokenRequest(request);

        Assertions.assertThat(user.getId()).isEqualTo(userId);
    }
}