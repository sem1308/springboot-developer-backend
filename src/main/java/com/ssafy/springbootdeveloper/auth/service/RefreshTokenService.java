package com.ssafy.springbootdeveloper.auth.service;

import com.ssafy.springbootdeveloper.auth.domain.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RefreshTokenService {
    RefreshToken findByRefreshToken(String refreshToken);
    void saveRefreshToken(Long userId, String refreshToken);

    void removeRefreshToken(Long userId);
    void removeRefreshToken(String refreshToken);
    void removeRefreshToken(HttpServletRequest request, HttpServletResponse response);

    void addRefreshTokenToCookie(HttpServletRequest request,
                                 HttpServletResponse response, String refreshToken);
    void removeRefreshTokenFromCookie(HttpServletRequest request, HttpServletResponse response);
}
