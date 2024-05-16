package com.spring.security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/list")
    private ResponseEntity<List<UserEntity>> list() {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

}
