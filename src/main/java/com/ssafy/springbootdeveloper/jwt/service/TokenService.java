package com.ssafy.springbootdeveloper.jwt.service;

public interface TokenService {
    String createNewAccessToken(String refreshToken);
}
