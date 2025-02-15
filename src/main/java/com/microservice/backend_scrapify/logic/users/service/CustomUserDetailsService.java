package com.microservice.backend_scrapify.logic.users.service;

import com.microservice.backend_scrapify.logic.users.entity.Users;
import com.microservice.backend_scrapify.logic.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findById(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found For Given Id");
        }
        return new User(
                user.get().getEmail(),
                user.get().getPassword(),
                new ArrayList<>()
        );
    }

}
