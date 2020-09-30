package com.springboot.webApp.controller;

import com.springboot.webApp.model.User;
import com.springboot.webApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    //display list of users
    @GetMapping("/usersTable")
    public String viewUsersTable(Model model){
        model.addAttribute("listUsers", userService.getAllUsers());
        return "usersTableIndex";
    }
    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model){
        //Create model atribute to bind next form data (in new_user.html)
        User user= new User();
        model.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        //Save user to DB
        userService.saveUser(user);
        return "redirect:/usersTable";
    }
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value="id") long id, Model model){
        //get User from the service
        User user = userService.getUserById(id);
        //set User as a model attribute to pre-populate the next form data (in update_user)
        model.addAttribute("user", user);
        return "update_user";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value="id") long id, Model model){
        //call delete user method
        this.userService.deleteUserByID(id);
        return "redirect:/usersTable";
    }
//testing json ajax
    @GetMapping("/showAllUsersAjax")
    public String test(){
        return "showAllUsersAJAX";
    }


/*
    @RequestMapping("/availableName.do")
    public @ResponseBody String availableName(@RequestParam("firstName") String firstName){
        if (userService.checkFirstNameIfAvailable(firstName)){
            return "nombre de usuario disponible";
        }
        else{
            return "nombre de usuario <strong>no</strong> disponible";
        }
    }
    */

}
