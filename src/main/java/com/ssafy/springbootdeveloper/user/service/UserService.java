package com.ssafy.springbootdeveloper.user.service;

import com.ssafy.springbootdeveloper.auth.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    Long save(AddUserRequest request);
    User findById(Long userId);
    User findByEmail(String email);
    List<User> findAll();
    boolean isVaildUserInfo(CreateTokenRequest request);
    User findByTokenRequest(CreateTokenRequest request);
}
