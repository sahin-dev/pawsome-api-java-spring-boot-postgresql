package com.pawsome.api.auth;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pawsome.api.auth.dtos.LoginUserDto;
import com.pawsome.api.auth.dtos.SignupDto;
import com.pawsome.api.auth.exception.EntityAlreadyExistsException;
import com.pawsome.api.auth.repositories.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User Singup(SignupDto signupDto) throws EntityAlreadyExistsException{

        if(userRepository.existsByEmail(signupDto.getEmail())){
            throw new EntityAlreadyExistsException("Email already exists");
        }

        // if(userRepository.existsByUsername(signupDto.getUsername())){
        //     throw new EntityAlreadyExistsException("username already exists!");
        // }
        User user = new User()
        .setFullName(signupDto.getFullName())
        .setEmail(signupDto.getEmail())
        .setUsername(signupDto.getUsername())
        .setPassword(passwordEncoder.encode(signupDto.getPassword()));

        
        User createdUser =  userRepository.save(user);

        System.out.println(createdUser);

        return createdUser;
        
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return user;
    }
}
