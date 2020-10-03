package com.springboot.webApp.service;

import com.springboot.webApp.model.UserLogin;
import com.springboot.webApp.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements  UserLoginService{

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUserLogin(UserLogin userLogin) {

        userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
        userLoginRepository.save(userLogin);

    }
}
