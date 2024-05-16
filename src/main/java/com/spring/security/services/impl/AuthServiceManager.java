package com.spring.security.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.persistence.repositories.UserRepository;
import com.spring.security.services.AuthService;
import com.spring.security.services.JWTUtilityService;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;
import com.spring.security.services.models.validations.UserValidation;

@Service
public class AuthServiceManager implements AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTUtilityService service;

    @Autowired
    private UserValidation validation;

    private final Integer ENCRYPTION_LEVEL = 12;

    // It is more advisable to use a DTO due to resource issues
    // Since the HashMaps require / consume many more resources
    @Override
    public HashMap<String, String> login(LoginDTO login) throws Exception {
        try {
            HashMap<String, String> jwt = new HashMap<>();
            // Try seek user by email
            Optional<UserEntity> user = repository.findByEmail(login.getEmail());

            if (user.isEmpty()) {
                jwt.put("error", "User Not registered! / ¡Usuario no registrado!");
                return jwt;
            }

            // Verify the password
            if (verifyPassword(login.getPassword(), user.get().getPassword())) {
                jwt.put("jwt", service.generateJWT(user.get().getId()));
            } else {
                jwt.put("error", "Authentication failed / Error de autenticación");
            }
            return jwt;

        } catch (Exception e) {
            System.err.println(e.toString());
            throw new Exception(e.toString());
        }
    }

    @Override
    public ResponseDTO register(UserEntity user) throws Exception {
        try {
            ResponseDTO response = validation.validate(user);
            if (response.getNumOfErrors() > 0) {
                return response;
            }

            List<UserEntity> users = repository.findAll();
            for (UserEntity u : users) {
                if (u.getEmail().equals(user.getEmail())) {
                    response.setNumOfErrors(1);
                    response.setMessage("User already exist! / ¡El usuario ya existe!");
                    return response;
                }
            }

            // Save the password encrypted
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(ENCRYPTION_LEVEL);
            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
            response.setMessage("User created successfully! / ¡Usuario creado con éxito!");

            return response;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        // Modified and put encryption level to see if fixed the error
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }

}
