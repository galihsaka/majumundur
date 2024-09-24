package com.enigmacamp.majumundur.dto.response;

import com.enigmacamp.majumundur.entity.Role;
import com.enigmacamp.majumundur.entity.User;

import java.util.List;

public class RegisterResponse {
    private String id;
    private String username;
    private String fullName;
    private User.EGender gender;
    private List<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User.EGender getGender() {
        return gender;
    }

    public void setGender(User.EGender gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
