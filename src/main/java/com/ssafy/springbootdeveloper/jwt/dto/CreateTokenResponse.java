package com.ssafy.springbootdeveloper.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class CreateTokenResponse {
    private String accessToken;
    private String refreshToken;
}

