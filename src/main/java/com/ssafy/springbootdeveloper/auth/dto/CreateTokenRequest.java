package com.ssafy.springbootdeveloper.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

