package com.springboot.webApp.repository;

import com.springboot.webApp.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    UserLogin findByUsername(String username);
}
