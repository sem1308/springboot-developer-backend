package com.ssafy.springbootdeveloper.auth.controller;

import com.ssafy.springbootdeveloper.auth.dto.CreateAccessTokenRequest;
import com.ssafy.springbootdeveloper.auth.dto.CreateAccessTokenResponse;
import com.ssafy.springbootdeveloper.auth.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.auth.dto.CreateTokenResponse;
import com.ssafy.springbootdeveloper.auth.service.TokenService;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/refresh")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request
        ){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<CreateTokenResponse> login(@RequestBody CreateTokenRequest request){
//        System.out.println(request);
        User user = userService.findByTokenRequest(request);

        String accessToken = tokenService.createAccessToken(user);
        String refreshToken = tokenService.createRefreshToken(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateTokenResponse(accessToken,refreshToken));
    }
}