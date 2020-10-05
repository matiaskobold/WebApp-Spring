package com.springboot.webApp.controller;

import com.springboot.webApp.model.User;
import com.springboot.webApp.model.UserLogin;
import com.springboot.webApp.repository.UserRepository;
import com.springboot.webApp.service.UserLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("listUsers", userRepository.findAll());
        return "home";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/logout-success")
    public String logOut(){
        return "logout";
    }

    @RequestMapping("/newUserLogin")
    public String newUserLogin(Model model){
        UserLogin userLogin = new UserLogin();
        model.addAttribute("userLogin", userLogin);
        return "newUserLogin";
    }

    @PostMapping("/saveUserLogin")
    public String saveUser(@ModelAttribute("userLogin") UserLogin userLogin){
        //Save userLogin to DB
        userLoginService.saveUserLogin(userLogin);
        return "redirect:/";
    }
}
