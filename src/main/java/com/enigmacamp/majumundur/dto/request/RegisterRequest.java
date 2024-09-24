package com.enigmacamp.majumundur.dto.request;

import com.enigmacamp.majumundur.entity.Role;
import com.enigmacamp.majumundur.entity.User;

import java.util.List;

public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private User.EGender gender;
    private List<Role.ERole> roles;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User.EGender getGender() {
        return gender;
    }

    public void setGender(User.EGender gender) {
        this.gender = gender;
    }

    public List<Role.ERole> getRoles() {
        return roles;
    }

    public void setRoles(List<Role.ERole> roles) {
        this.roles = roles;
    }
}
