package com.ssafy.springbootdeveloper.jwt.service;

import com.ssafy.springbootdeveloper.config.jwt.TokenProvider;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    private final static Duration ACCESS_TOKEN_DURATION = Duration.ofHours(2);

    @Override
    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.vaildToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();

        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
    }
}
