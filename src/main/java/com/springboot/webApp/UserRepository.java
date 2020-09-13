package com.springboot.webApp;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.springboot.webApp.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
