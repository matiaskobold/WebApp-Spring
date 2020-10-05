package com.springboot.webApp.controller;

import com.springboot.webApp.model.Clan;
import com.springboot.webApp.model.User;
import com.springboot.webApp.repository.ClanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClanController {

    @Autowired
    ClanRepository clanRepository;

    @GetMapping("/clansTable")
    public String viewClansTable(Model model){
        model.addAttribute("listClans", clanRepository.findAll());
        return "clansTable";
    }


    @GetMapping("/showNewClanForm")
    public String showNewClanForm(Model model){
        //Create model atribute to bind next form data (in new_clan.html)
        Clan clan = new Clan();
        model.addAttribute("clan", clan);
        return "new_clan";
    }

    @PostMapping("/saveClan")
    public String saveClan(@ModelAttribute("clan") Clan clan){
        //Save clan to DB
        clanRepository.save(clan);
        return "redirect:/clansTable";
    }
    @GetMapping("/showClanFormForUpdate/{id}")
    public String showClanFormForUpdate(@PathVariable(value="id") long id, Model model){
        //get Clan from the repo
        Clan clan= clanRepository.getOne(id);
        //set Clan  as a model attribute to pre-populate the next form data (in update_user)
        model.addAttribute("clan", clan);
        return "update_clan";
    }

    @GetMapping("/deleteClan/{id}")
    public String deleteClan(@PathVariable(value="id") long id, Model model){
        //call delete clan method from repo
        clanRepository.deleteById(id);
        return "redirect:/clansTable";
    }
}
