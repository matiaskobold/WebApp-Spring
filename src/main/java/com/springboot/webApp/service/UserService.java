package com.springboot.webApp.service;

import com.springboot.webApp.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(long id);
    void deleteUserByID(long id);
    Boolean checkFirstNameIfAvailable(String firstName);

}
