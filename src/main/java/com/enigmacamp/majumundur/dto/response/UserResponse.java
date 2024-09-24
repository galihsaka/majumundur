package com.enigmacamp.majumundur.dto.response;

import com.enigmacamp.majumundur.entity.User;

public class UserResponse {
    private String username;
    private String fullName;
    private User.EGender gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
