package com.ssafy.springbootdeveloper.config.jwt;

import com.ssafy.springbootdeveloper.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user) {
        Date now = new Date();
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
            // 내용 iss : hshhan0221@naver.com(propertise 파일에서 설정한 값)
            .setIssuer(jwtProperties.getIssuer())
            .setIssuedAt(now) // 내용 iat : 현재 시간
            .setExpiration(expiry) // 내용 exp : expiry 멤버 변숫값
            .setSubject(user.getEmail()) // 내용 sub : 유저의 이메일
            .claim("id", user.getId()) // 클레임 id : 유저 ID
            // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
            .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .compact();
    }

    public boolean vaildToken(String token){
        try{
            Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        // TODO : 실제 user의 권한 가져오기
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new
            SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(User.builder().email(claims.getSubject()).password("").authorities(authorities).build(), token, authorities);
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }
    private Claims getClaims(String token) {
        return Jwts.parser() // 클레임 조회
            .setSigningKey(jwtProperties.getSecretKey())
            .parseClaimsJws(token)
            .getBody();
    }

}
