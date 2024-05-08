package com.ssafy.springbootdeveloper.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateTokenResponse {
    private String accessToken;
    private String refreshToken;
}
