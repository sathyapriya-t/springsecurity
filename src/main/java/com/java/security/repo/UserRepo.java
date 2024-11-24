package com.java.security.repo;

import com.java.security.domin.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<Users, Integer> {

    Users findByUsername(String username);
}
