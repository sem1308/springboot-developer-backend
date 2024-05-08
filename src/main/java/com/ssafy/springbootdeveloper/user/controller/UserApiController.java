package com.ssafy.springbootdeveloper.user.controller;

import com.ssafy.springbootdeveloper.config.jwt.TokenProvider;
import com.ssafy.springbootdeveloper.jwt.service.TokenService;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import com.ssafy.springbootdeveloper.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> signUp(@RequestBody AddUserRequest request){
        long id = userService.save(request);
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public ResponseEntity<User> getUser(Authentication authentication){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }
}