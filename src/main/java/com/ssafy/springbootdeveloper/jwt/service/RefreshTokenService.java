package com.ssafy.springbootdeveloper.jwt.service;

import com.ssafy.springbootdeveloper.jwt.domain.RefreshToken;

public interface RefreshTokenService {
    RefreshToken findByRefreshToken(String refreshToken);
}
