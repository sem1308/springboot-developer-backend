package com.ssafy.springbootdeveloper.user.repository;

import com.ssafy.springbootdeveloper.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Long countByEmailAndPassword(String email, String password);
    Optional<User> findByEmailAndPassword(String email, String password);
}
