package com.ssafy.springbootdeveloper.user.service;

import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    Long save(AddUserRequest request);

}
