package com.ssafy.springbootdeveloper.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddUserRequest {
    private String email;
    private String password;
}
