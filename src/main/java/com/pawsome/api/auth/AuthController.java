package com.pawsome.api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pawsome.api.auth.dtos.LoginResponse;

import com.pawsome.api.auth.dtos.LoginUserDto;
import com.pawsome.api.auth.dtos.SignupDto;

import jakarta.validation.Valid;


@RestController()
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(
        AuthService authService,
        JwtService jwtService
    ){
        this.authService = authService;
        this.jwtService =  jwtService;
    }

    @GetMapping(path="signup")
    public ResponseEntity<?> SignUp(@RequestBody @Valid SignupDto signupDto){
  
        return ResponseEntity.status(201).body(authService.Singup(signupDto));
    }

    @GetMapping(path = "login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto){
        User authenticatedUser = authService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        
        LoginResponse loginResponseDto = new LoginResponse().setToken(jwtToken);

        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping(path = "me")
    public ResponseEntity<User> getAuthenticatedUser(){

        return ResponseEntity.ok(authService.getAuthenticatedUser());
    }

}
