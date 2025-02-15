package com.microservice.backend_scrapify.logic.users.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptingService {

    public String encrypt(String password) {
        return BCrypt.hashpw(
                password, BCrypt.gensalt());
    }

    public boolean checkEncryption(String password, String hashedPassword) {
        return BCrypt.checkpw(
                password, hashedPassword);
    }

}
