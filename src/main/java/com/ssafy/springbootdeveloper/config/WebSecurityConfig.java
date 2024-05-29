//package com.ssafy.springbootdeveloper.config;
//
//import com.ssafy.springbootdeveloper.auth.service.TokenProvider;
//import com.ssafy.springbootdeveloper.filter.TokenAuthenticationFilter;
//import com.ssafy.springbootdeveloper.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//
//import java.util.Collections;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//    // 스프링 시큐리티 기능 비활성화
//    // h2 console만 시큐리티 기능 사용을 비활성화
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//            .requestMatchers(toH2Console());
//    }
//
//    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http, TokenProvider tokenProvider) throws Exception {
//        return http
//            .authorizeHttpRequests() // 인증, 인가 설정
//            .requestMatchers("/api/articles/**","/api/auth/**").permitAll()
//            .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .csrf().disable() // csrf 비활성화
//            .cors(corsCustomizer -> corsCustomizer.configurationSource((request) ->{
//                    CorsConfiguration config = new CorsConfiguration();
//                    config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
//                    config.setAllowedMethods(Collections.singletonList("*"));
//                    config.setAllowCredentials(true);
//                    config.setAllowedHeaders(Collections.singletonList("*"));
//                    config.setMaxAge(3600L); //1시간
//                    return config;
//            }))
//            .addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
//            .build();
//    }
//
//    // 인증 관리자 관련 설정
//    @Bean
//    public AuthenticationManager authenticationManager(
//        HttpSecurity http,
//        BCryptPasswordEncoder bCryptPasswordEncoder,
//        UserService userService
//    ) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//            .userDetailsService(userService)
//            .passwordEncoder(bCryptPasswordEncoder)
//            .and()
//            .build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
