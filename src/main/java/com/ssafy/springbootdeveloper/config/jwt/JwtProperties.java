package com.ssafy.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey;
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public final static Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public final static Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
}
