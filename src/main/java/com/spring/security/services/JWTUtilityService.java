package com.spring.security.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

public interface JWTUtilityService {

    String generateJWT(Long userId) throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, JOSEException;

    JWTClaimsSet parseJWT(String jwt) throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, ParseException, JOSEException;

}
