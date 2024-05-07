package com.ssafy.springbootdeveloper.jwt.service;

import com.ssafy.springbootdeveloper.jwt.domain.RefreshToken;
import com.ssafy.springbootdeveloper.jwt.repository.RefreshTokenRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenRespository refreshTokenRespository;

    @Override
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRespository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
