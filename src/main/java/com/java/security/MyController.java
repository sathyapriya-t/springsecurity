package com.java.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        //getting session id using HttpServletRequest - this is session id which run in chrome
        return "Hello! Welcome to the page!!" + request.getSession().getId();
    }
}
