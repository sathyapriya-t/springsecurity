package com.java.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// EnableWebSecurity ->this annotation basically tells the spring leave the default config you have for spring security and use this.
// with this the basic form page will be disabled and what ever we are configuring down will work
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        /*

        //CSRF - Cross Site Request Forgery, this is an attack which tricks he te get access to the unwanted request.
        // Basically it will have same session id for all the request and stored in a cookie, which can be accessed to use dangerously.
        // So for that we need to make session id uniques for each request(Independent of user/client , seesion id should be unique).
        // That's called stateless application.
        //By default, csrf is enabled by spring security.To make the application stateless we are disabling it
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        //this set of rules to determine if request is allowed or denied
        httpSecurity.authorizeHttpRequests(customer -> customer.anyRequest().authenticated());

        //enables form login. and need to comment it for session if to work
        //httpSecurity.formLogin(Customizer.withDefaults());
        //For postman to work
        httpSecurity.httpBasic(Customizer.withDefaults());

        //This sessionManagement determines what type of session we're using and here making it state less - uniques session fo each request
        httpSecurity.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //Creating the object
        return httpSecurity.build();

        */

        //This can also be writen like this
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
