package com.ssafy.springbootdeveloper.auth.service;

import com.ssafy.springbootdeveloper.auth.domain.RefreshToken;
import com.ssafy.springbootdeveloper.auth.repository.RefreshTokenRepository;
import com.ssafy.springbootdeveloper.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.springbootdeveloper.config.jwt.JwtProperties.REFRESH_TOKEN_COOKIE_NAME;
import static com.ssafy.springbootdeveloper.config.jwt.JwtProperties.REFRESH_TOKEN_DURATION;

@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
    // 생성된 리프레시 토큰을 전달받아 데이터베이스에 저장
    @Override
    public void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void removeRefreshToken(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
    @Transactional
    @Override
    public void removeRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    @Transactional
    @Override
    public void removeRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = CookieUtil.getCookie(request, REFRESH_TOKEN_COOKIE_NAME);
        String refreshToken = cookie.getValue();
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
        CookieUtil.deleteCookie(cookie,response);
    }

    // 생성된 리프레시 토큰을 쿠키에 저장
    @Override
    public void addRefreshTokenToCookie(HttpServletRequest request,
                                         HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken,
                cookieMaxAge);
    }

    @Override
    public void removeRefreshTokenFromCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
    }
}
