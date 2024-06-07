package com.ssafy.springbootdeveloper.user.service;

import com.ssafy.springbootdeveloper.auth.dto.CreateTokenRequest;
import com.ssafy.springbootdeveloper.user.domain.User;
import com.ssafy.springbootdeveloper.user.dto.AddUserRequest;
import com.ssafy.springbootdeveloper.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
            .orElseThrow(()->new IllegalArgumentException(email));
    }

    @Override
    public Long save(AddUserRequest request) {
        return userRepository.save(
            User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build()
        ).getId();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean isVaildUserInfo(CreateTokenRequest request) {
        long cnt = userRepository.countByEmailAndPassword(request.getEmail(), bCryptPasswordEncoder.encode(request.getPassword()));
        return cnt > 0;
    }

    @Override
    public User findByTokenRequest(CreateTokenRequest request) {
        User user = findByEmail(request.getEmail());
        System.out.println(user);

        if(bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())){
            return user;
        }else{
            throw new IllegalArgumentException("Unexpected user");
        }
    }
}
