package com.ssafy.springbootdeveloper.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateTokenResponse {
    private String accessToken;
    private String refreshToken;
}

