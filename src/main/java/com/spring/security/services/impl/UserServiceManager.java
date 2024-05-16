package com.spring.security.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.persistence.repositories.UserRepository;
import com.spring.security.services.UserService;

@Service
public class UserServiceManager implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public List<UserEntity> list() {
        return repository.findAll();
    }

}
