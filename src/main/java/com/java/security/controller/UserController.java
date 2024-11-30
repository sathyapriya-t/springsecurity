package com.java.security.controller;

import com.java.security.domin.Users;
import com.java.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        System.out.println("coming here controller");
        return userService.register(user);
    }

    @GetMapping("/fetch")
    public Iterable<Users> register(){
        return userService.fetchUsers();
    }

    @GetMapping("/login")
    public String login(@RequestBody Users users){
        System.out.println("coming here controller");
        return userService.verify(users);
    }

}
