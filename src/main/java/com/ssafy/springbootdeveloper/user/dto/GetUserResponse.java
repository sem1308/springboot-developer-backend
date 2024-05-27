package com.ssafy.springbootdeveloper.user.dto;

import com.ssafy.springbootdeveloper.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
public class GetUserResponse {

    private Long id;

    private String email;

    LocalDateTime createdDateTime;

    LocalDateTime updatedDateTime;

    Set<? extends GrantedAuthority> authorities;

    @Builder
    public GetUserResponse(Long id, String email,LocalDateTime createdDateTime,LocalDateTime updatedDateTime, Set<? extends GrantedAuthority> authorities){
        this.id = id;
        this.email = email;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.authorities = authorities;
    }

    public GetUserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.createdDateTime = user.getCreatedDateTime();
        this.updatedDateTime = user.getUpdatedDateTime();
        this.authorities = user.getAuthorities();
    }
}
