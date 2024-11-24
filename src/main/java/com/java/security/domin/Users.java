package com.java.security.domin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Users {

    @Id
    private int userId;
    private String username;
    private String password;
    private String role;
}
