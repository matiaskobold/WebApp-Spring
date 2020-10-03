package com.springboot.webApp.service;

import com.springboot.webApp.model.User;
import com.springboot.webApp.model.UserLogin;

public interface UserLoginService {
    void saveUserLogin(UserLogin userLogin);
}
