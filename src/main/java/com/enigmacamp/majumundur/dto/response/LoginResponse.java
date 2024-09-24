package com.enigmacamp.majumundur.dto.response;

import com.enigmacamp.majumundur.entity.Role;

import java.util.List;

public class LoginResponse {
    private String username;
    private String token;
    private List<Role.ERole> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role.ERole> getRoles() {
        return roles;
    }

    public void setRoles(List<Role.ERole> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
