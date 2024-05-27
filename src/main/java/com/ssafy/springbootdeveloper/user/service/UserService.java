package com.ssafy.springbootdeveloper.user.service;

import com.ssafy.springbootdeveloper.auth.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    Long save(AddUserRequest request);
    User findById(Long userId);
    boolean isVaildUserInfo(CreateTokenRequest request);
    User findByTokenRequest(CreateTokenRequest request);
}
