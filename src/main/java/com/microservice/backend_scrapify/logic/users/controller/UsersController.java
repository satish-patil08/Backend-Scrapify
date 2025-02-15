package com.microservice.backend_scrapify.logic.users.controller;


import com.microservice.backend_scrapify.logic.users.entity.Users;
import com.microservice.backend_scrapify.logic.users.service.UserCRUDOps;
import com.microservice.backend_scrapify.logic.users.service.UserLoginService;
import com.microservice.backend_scrapify.modelRequest.LoginVerificationRequest;
import com.microservice.backend_scrapify.modelResponse.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserCRUDOps userCRUDOps;

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/register")
    public StatusResponse createUser(@RequestBody Users users) {
        return userCRUDOps.createUser(users);
    }

    @PostMapping("/login")
    public StatusResponse loginUser(@RequestBody LoginVerificationRequest loginVerificationRequest) {
        return userLoginService.loginUser(loginVerificationRequest);
    }

}
