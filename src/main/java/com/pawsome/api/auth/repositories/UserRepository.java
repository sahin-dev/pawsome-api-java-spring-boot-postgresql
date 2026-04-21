package com.pawsome.api.auth.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pawsome.api.auth.User;




public interface UserRepository extends CrudRepository<User, String>{
    
    Optional<User> findByEmail(String email);
    User save(User user);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
