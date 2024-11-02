package com.mk.journalApp.controller;

import com.mk.journalApp.entity.User;
import com.mk.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @GetMapping("/health-check")
    public String checkHealth(){
        return "ok";
    }

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user){
        userService.saveNewUser(user);
        return user;
    }
}
