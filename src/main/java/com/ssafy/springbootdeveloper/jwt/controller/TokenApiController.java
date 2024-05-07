package com.ssafy.springbootdeveloper.jwt.controller;

import com.ssafy.springbootdeveloper.jwt.dto.CreateAccessTokenRequest;
import com.ssafy.springbootdeveloper.jwt.dto.CreateAccessTokenResponse;
import com.ssafy.springbootdeveloper.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/token")
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request
        ){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}