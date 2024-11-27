package com.java.security.service;

import com.java.security.domin.Users;
import com.java.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        //here only we are register the user, while registering itself we can encode it
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Iterable<Users> fetchUsers() {
        return userRepo.findAll();
    }

    public String verify(Users users) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(),users.getPassword()));
        if (authentication.isAuthenticated()){
            return "Success";
        }
        return "Failed";
    }
}
