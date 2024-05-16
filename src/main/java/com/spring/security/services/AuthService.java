package com.spring.security.services;

import java.util.HashMap;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;

public interface AuthService {

    HashMap<String, String> login(LoginDTO login) throws Exception;

    ResponseDTO register(UserEntity user) throws Exception;

}
