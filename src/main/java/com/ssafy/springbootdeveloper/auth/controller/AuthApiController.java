package com.ssafy.springbootdeveloper.auth.controller;

import com.ssafy.springbootdeveloper.auth.dto.CreateAccessTokenRequest;
import com.ssafy.springbootdeveloper.auth.dto.CreateAccessTokenResponse;
import com.ssafy.springbootdeveloper.auth.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.auth.dto.CreateTokenResponse;
import com.ssafy.springbootdeveloper.auth.service.TokenService;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.service.UserService;
import com.ssafy.springbootdeveloper.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        System.out.println("[REFRESH]");
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
        System.out.println("newAccessToken : " + newAccessToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<CreateTokenResponse> login(@RequestBody CreateTokenRequest tokenRequest, HttpServletRequest request, HttpServletResponse response){
        System.out.println(tokenRequest);
        User user = userService.findByTokenRequest(tokenRequest);
        System.out.println(user);

        String accessToken = tokenService.createAccessToken(user);
        String refreshToken = tokenService.createRefreshToken(user);
        tokenService.addRefreshTokenToCookie(request,response,refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateTokenResponse(accessToken,refreshToken));
    }

    @GetMapping("/logout")
    public ResponseEntity<CreateTokenResponse> logout(HttpServletRequest request, HttpServletResponse response){
        System.out.println("[LOGOUT]");
        tokenService.removeRefreshToken(request, response);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}