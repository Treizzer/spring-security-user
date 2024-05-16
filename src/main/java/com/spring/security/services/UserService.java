package com.spring.security.services;

import java.util.List;

import com.spring.security.persistence.entities.UserEntity;

public interface UserService {

    List<UserEntity> list();

}
