package com.springboot.webApp.service;

import com.springboot.webApp.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(long id);
    void deleteUserByID(long id);

}
