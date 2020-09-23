package com.springboot.webApp.service;

import com.springboot.webApp.model.User;
import com.springboot.webApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        Optional<User> optional = userRepository.findById(id);
        User user = null;
        if(optional.isPresent()){
            user = optional.get();
        } else{
            throw  new RuntimeException("User not found for id ::" +id);
        }
        return user;
    }

    @Override
    public void deleteUserByID(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Boolean checkFirstNameIfAvailable(String firstName) {
        List<User> users;
        users=userRepository.findAll();
        Iterator<User> iterator=users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getFirst_name().equals(firstName)) {
                return false;
            }
        }
        return true;
    }
}
