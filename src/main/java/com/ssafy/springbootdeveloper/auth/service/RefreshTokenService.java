package com.ssafy.springbootdeveloper.auth.service;

import com.ssafy.springbootdeveloper.auth.domain.RefreshToken;

public interface RefreshTokenService {
    RefreshToken findByRefreshToken(String refreshToken);
}
