package com.ssafy.springbootdeveloper.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootdeveloper.auth.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
@Transactional
class UserServiceImplTest {
    @Autowired UserService userService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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

    @DisplayName("findByEmail : email로 유저 찾기에 성공한다.")
    @Test
    public void findByEmail(){
        //given
        String email = "test@test.com";
        String password = "test";

        AddUserRequest request = AddUserRequest.builder()
                .email(email)
                .password(password)
                .build();

        userService.save(request);

        //when
        User user = userService.findByEmail(email);

        //then
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
        Assertions.assertThat(passwordEncoder.matches(password,user.getPassword())).isEqualTo(true);
    }
}