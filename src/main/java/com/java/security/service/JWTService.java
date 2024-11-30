package com.java.security.service;
import com.java.security.domin.Users;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Fist we need to send the login request and get the jwt token
//Then, Using the jwt we can authorize and get the data regarding student details

@Service
public class JWTService {

    private String secretKey;

    public JWTService() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey key = generator.generateKey();
        secretKey = Base64.getEncoder().encodeToString(key.getEncoded());


    }
    public String generateToken(Users user) {

        //For generating token, we need to build Jwts with claim, subject,issueddate,expiration date and sighWith Key
        //to set the key to sign with -> we need Key.hmacShaKeyFor(bytes) -> this takes the bytes
        //To set the key we need to generate it -> that can be done by using KeyGenerator object
        //After key got generated we need to encode it to Base64.getEncoder to encode
        //After before setting the key, we need to decode it with Base64 and pass it keys.hmacShaKeyFor(bytes)
        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*30))
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String extractUserName(String token) {
        return "";
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return true;
    }
}
