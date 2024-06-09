package com.ssafy.springbootdeveloper.auth.service;

import com.ssafy.springbootdeveloper.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService extends RefreshTokenService {
    String createNewAccessToken(String refreshToken);
    String createAccessToken(User user);
    String createRefreshToken(User user);
}