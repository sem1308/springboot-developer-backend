package com.ssafy.springbootdeveloper.filter;

import com.ssafy.springbootdeveloper.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 요청 정보에서 Authorization header값 가져옴
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // Authorization header값에서 token 추출
        String token = getAccessToken(authorizationHeader);
        // 토큰 유효성 검사
        if(tokenProvider.vaildToken(token)){
            // 유효하다면 토큰에서 인증정보(객체) 가져옴
            Authentication authentication = tokenProvider.getAuthentication(token);
            // 인증 객체 시큐리티 컨텍스트 홀더에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            // Authorization헤더가 존재하고 그 값이 Bearer 로 시작한다면 - Jwt 토큰이 설정되었다 판단
            return authorizationHeader.substring(TOKEN_PREFIX.length()); // Bearer 뒤의 값인 Jwt 토큰 값을 추출
        }
        return null;
    }
}
