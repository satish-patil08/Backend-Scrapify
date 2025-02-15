package com.microservice.backend_scrapify.modelRequest;

public class LoginVerificationRequest {
    public String email;
    public String password;

    public LoginVerificationRequest() {
    }

    public LoginVerificationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVerificationRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
