package com.ssafy.springbootdeveloper.jwt.controller;

import com.ssafy.springbootdeveloper.jwt.dto.CreateAccessTokenRequest;
import com.ssafy.springbootdeveloper.jwt.dto.CreateAccessTokenResponse;
import com.ssafy.springbootdeveloper.jwt.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.jwt.dto.CreateTokenResponse;
import com.ssafy.springbootdeveloper.jwt.service.TokenService;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/token")
public class TokenApiController {
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