package com.ssafy.springbootdeveloper.user.controller;

import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import com.ssafy.springbootdeveloper.user.dto.GetUserResponse;
import com.ssafy.springbootdeveloper.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> signUp(@RequestBody AddUserRequest request){
        long id = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public ResponseEntity<GetUserResponse> getUser(Authentication authentication){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof User u){
            User user = userService.findByEmail(u.getEmail());
            GetUserResponse userResponse = new GetUserResponse(user);
            return ResponseEntity.ok(userResponse);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}