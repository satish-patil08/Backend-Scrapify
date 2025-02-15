package com.microservice.backend_scrapify.utils.jwtUtils;

public class CustomUserDetails {
    public String username;
    public String password;

    public CustomUserDetails() {
    }

    public CustomUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
