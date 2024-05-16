package com.spring.security.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.services.AuthService;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Dependencies injection, you can do it over autowired or contructor
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    private ResponseEntity<ResponseDTO> register(@RequestBody UserEntity user) throws Exception {
        return new ResponseEntity<>(service.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<HashMap<String, String>> login(@RequestBody LoginDTO loginDTO) throws Exception {
        HashMap<String, String> login = service.login(loginDTO);

        if (login.containsKey("jwt")) { // "jwt" was defined in "AuthServiceManager"
            return new ResponseEntity<>(login, HttpStatus.OK);
        }

        return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
    }

}
