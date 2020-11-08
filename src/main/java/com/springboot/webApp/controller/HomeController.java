package com.springboot.webApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.webApp.model.User;
import com.springboot.webApp.model.UserLogin;
import com.springboot.webApp.repository.UserRepository;
import com.springboot.webApp.service.UserLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.webApp.apiREST.ResourceNotFoundException;
import com.springboot.webApp.model.Clan;
import com.springboot.webApp.repository.ClanRepository;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.ws.Response;

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
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @RequestMapping("/publicAPI")
    public String publicAPI(Model model, RestTemplate restTemplate) throws JsonProcessingException {
        int gameSteamID=730;
        int numberOfNews=3;
        int maxLengthOfNews=300;
        String formatResponse="json";
/*
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appid",gameSteamID);
        map.add("count", numberOfNews);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,headers);

        ResponseEntity<String> responseStr = restTemplate.getForEntity(
                "http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/",
                String.class,
                request);

 */
        ResponseEntity<String> responseStr=restTemplate.getForEntity("http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?"+
                "appid="+gameSteamID+
                "&count="+numberOfNews+
                "&maxlength="+maxLengthOfNews+
                "&format="+formatResponse,
                String.class);



        model.addAttribute("response", responseStr);
        System.out.println(responseStr);
        return "publicApi";
    }

}
