package com.springboot.webApp.controller;

import com.springboot.webApp.model.User;
import com.springboot.webApp.repository.ClanRepository;
import com.springboot.webApp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClanRepository clanRepository;
    //display list of users
    @GetMapping("/usersTable")
    public String viewUsersTable(Model model){
        model.addAttribute("listUsers", userRepository.findAll());
        return "usersTable";
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model){
        //Create model attribute to bind next form data (in new_user.html)
        if (clanRepository.findAll().isEmpty()){
            //String errorMessage = "No se han creado clanes, cree un clan primero para luego agregar un usuario.";
            //model.addAttribute("errorMessage", errorMessage);
            return "redirect:/showNewClanForm";
        }
        else {
            User user= new User();
            model.addAttribute("listClans", clanRepository.findAll());
            model.addAttribute("user", user);
            return "new_user";
        }



    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        //Save user to DB
        userRepository.save(user);
        return "redirect:/usersTable";
    }
    @GetMapping("/showUserFormForUpdate/{id}")
    public String showUserFormForUpdate(@PathVariable(value="id") long id, Model model){
        //get User from the service
        User user = userRepository.getOne(id);
        //set User as a model attribute to pre-populate the next form data (in update_user)
        model.addAttribute("listClans", clanRepository.findAll());
        model.addAttribute("user", user);
        return "update_user";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value="id") long id, Model model){
        //call delete user method
        userRepository.deleteById(id);
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
