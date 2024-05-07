package com.ssafy.springbootdeveloper.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTokenRequest {
    private String email;
    private String password;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateTokenRequest{");
        sb.append("email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

