package com.microservice.backend_scrapify.logic.users.service;


import com.microservice.backend_scrapify.logic.users.entity.Users;
import com.microservice.backend_scrapify.logic.users.repository.UserRepository;
import com.microservice.backend_scrapify.modelResponse.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserCRUDOps {

    @Autowired
    private EncryptingService encryptingService;

    @Autowired
    private UserRepository userRepository;

    public StatusResponse createUser(Users users) {

        if (userRepository.findById(users.getEmail()).isEmpty()) {
            if (users.getPassword() == null) users.setPassword(UUID.randomUUID().toString());

            users.setCreateDateTime(new Date());
            users.setPassword(encryptingService.encrypt(users.getPassword()));

            Users saveUser = userRepository.save(users);
            return new StatusResponse(
                    true,
                    "User Created Successfully",
                    saveUser
            );

        }
        return new StatusResponse(
                false,
                "User with That Email Id Already Exist"
        );

    }
}
