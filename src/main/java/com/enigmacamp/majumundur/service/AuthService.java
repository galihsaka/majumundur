package com.enigmacamp.majumundur.service;

import com.enigmacamp.majumundur.dto.request.LoginRequest;
import com.enigmacamp.majumundur.dto.request.RegisterRequest;
import com.enigmacamp.majumundur.dto.response.LoginResponse;
import com.enigmacamp.majumundur.dto.response.RegisterResponse;

public interface AuthService {
    public RegisterResponse register(RegisterRequest request);
    public LoginResponse login(LoginRequest request);
}
