package com.ssafy.springbootdeveloper.auth.handler;

import com.ssafy.springbootdeveloper.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.ssafy.springbootdeveloper.auth.service.RefreshTokenService;
import com.ssafy.springbootdeveloper.auth.service.TokenProvider;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.ssafy.springbootdeveloper.config.jwt.JwtProperties.ACCESS_TOKEN_DURATION;
import static com.ssafy.springbootdeveloper.config.jwt.JwtProperties.REFRESH_TOKEN_DURATION;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        refreshTokenService.saveRefreshToken(user.getId(), refreshToken);
        refreshTokenService.addRefreshTokenToCookie(request, response, refreshToken);
        // 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String targetUrl = tokenProvider.getTargetUrl(accessToken);
        // 인증 관련 설정값, 쿠키 제거
        clearAuthenticationAttributes(request, response);
        // 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
    // 인증 관련 설정값, 쿠키 제거
    private void clearAuthenticationAttributes(HttpServletRequest request,
                                               HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request,
            response);
    }
}
