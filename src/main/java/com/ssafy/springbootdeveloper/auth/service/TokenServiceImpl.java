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

@Service
public class TokenServiceImpl extends RefreshTokenServiceImpl implements TokenService {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    public TokenServiceImpl(RefreshTokenRepository refreshTokenRepository, TokenProvider tokenProvider, UserService userService) {
        super(refreshTokenRepository);
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Override
    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.vaildToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = findByRefreshToken(refreshToken).getUserId();
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
        saveRefreshToken(user.getId(), refreshToken);
        return refreshToken;
    }
}