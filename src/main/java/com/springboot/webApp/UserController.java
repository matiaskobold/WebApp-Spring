package com.springboot.webApp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.*;

import javax.swing.text.html.parser.Entity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Controller
@RequestMapping(path="/demo")
class UserController {
    @Autowired
    private UserRepository repository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String first_name, @RequestParam String last_name, @RequestParam String username, @RequestParam String mail_address){
        User n = new User();
        n.setUsername(username);
        n.setFirst_name(first_name);
        n.setLast_name(last_name);
        n.setMail_address(mail_address);
        repository.save(n);
        return "Saved";
    }

    @GetMapping(path="all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return repository.findAll();
    }
}