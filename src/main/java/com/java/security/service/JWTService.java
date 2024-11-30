package com.java.security.service;

import com.java.security.domin.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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

        //For generating token, we need to build Jwts with claim, subject,issued date,expiration date and sighWith Key
        //to set the key to sign with -> we need Key.hmacShaKeyFor(bytes) -> this takes the bytes
        //To set the key we need to generate it -> that can be done by using KeyGenerator object
        //After key got generated we need to encode it to Base64.getEncoder to encode
        //After before setting the key, we need to decode it with Base64 and pass it keys.hmacShaKeyFor(bytes)
        Map<String, Object> claims = new HashMap<>();

        System.out.println("token ");
        String compact = Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                .and()
                .signWith(getKey())
                .compact();

        System.out.println("token "+ compact);
        return compact;
    }

    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        Date date = extractClaim(token, Claims::getExpiration);
        return date.before(new Date());

    }
}
