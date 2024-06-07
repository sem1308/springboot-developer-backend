package com.ssafy.springbootdeveloper.auth.service;

import com.ssafy.springbootdeveloper.config.jwt.JwtProperties;
import com.ssafy.springbootdeveloper.auth.domain.RefreshToken;
import com.ssafy.springbootdeveloper.auth.repository.RefreshTokenRepository;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Override
    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.vaildToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();

        User user = userService.findById(userId);

        return createAccessToken(user);
    }

    @Override
    public String createAccessToken(User user) {
        return tokenProvider.generateToken(user, JwtProperties.ACCESS_TOKEN_DURATION);
    }

    @Override
    public String createRefreshToken(User user) {
        String refreshToken = tokenProvider.generateToken(user, JwtProperties.REFRESH_TOKEN_DURATION);
        refreshTokenService.saveRefreshToken(user.getId(), refreshToken);
        return refreshToken;
    }

    @Override
    public void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        refreshTokenService.addRefreshTokenToCookie(request, response, refreshToken);
    }
}