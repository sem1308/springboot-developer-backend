package com.ssafy.springbootdeveloper.auth.service;

import com.ssafy.springbootdeveloper.user.domain.User;

public interface TokenService {
    String createNewAccessToken(String refreshToken);
    String createAccessToken(User user);
    String createRefreshToken(User user);
}