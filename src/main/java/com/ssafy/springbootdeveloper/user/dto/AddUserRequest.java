package com.ssafy.springbootdeveloper.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AddUserRequest {
    private String email;
    private String password;
}
