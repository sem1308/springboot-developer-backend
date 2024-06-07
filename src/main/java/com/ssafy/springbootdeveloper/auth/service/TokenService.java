package com.ssafy.springbootdeveloper.auth.service;

import com.ssafy.springbootdeveloper.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {
    String createNewAccessToken(String refreshToken);
    String createAccessToken(User user);
    String createRefreshToken(User user);

    void addRefreshTokenToCookie(HttpServletRequest request,
                                 HttpServletResponse response, String refreshToken);
}