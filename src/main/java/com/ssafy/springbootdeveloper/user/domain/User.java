package com.ssafy.springbootdeveloper.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "users")
@NoArgsConstructor
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // 사용자 이름
    @Column(name = "nickname", unique = true)
    private String nickname;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @CreatedDate
    @Column(name = "created_date_time")
    LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(name = "updated_date_time")
    LocalDateTime updatedDateTime;

    @Transient
    Set<? extends GrantedAuthority> authorities;

    @Builder
    public User(String email, String password, String nickname, Set<? extends GrantedAuthority> authorities){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authorities = authorities;
    }

    // 사용자 이름 변경
    public User update(String nickname){
        this.nickname = nickname;
        return this;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
