package com.springboot.webApp.service;

import com.springboot.webApp.model.UserLogin;
import com.springboot.webApp.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserLoginRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserLogin userLogin = repository.findByUsername(username);
        if(userLogin ==null){
            throw new UsernameNotFoundException("User 404");
        }
        return new UserLoginDetailsImpl(userLogin);

    }
}
