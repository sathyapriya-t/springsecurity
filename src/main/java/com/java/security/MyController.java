package com.java.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/")
    public String greet(){
        return "Hello! Welcome to the page!!";
    }
}
