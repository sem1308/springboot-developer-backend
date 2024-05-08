package com.ssafy.springbootdeveloper.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccessTokenResponse {
    private String accessToken;
}

