package com.java.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

// EnableWebSecurity ->this annotation basically tells the spring leave the default config you have for spring security and use this.
// with this the basic form page will be disabled and what ever we are configuring down will work
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    CustomAuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

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
        return http.csrf(customizer -> customizer.disable()).
                authorizeHttpRequests(request -> request
                        .requestMatchers("/login", "/register").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint)).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //Authentication is basically done through authentication provider
    //By default spring will have some authentication provider -> by creating this bean we are saying use this authentication not the default one
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        //Adding password encoder to protect the password while verifying -> if we didn't add it, we need to give the encrypted value as password
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }


}
