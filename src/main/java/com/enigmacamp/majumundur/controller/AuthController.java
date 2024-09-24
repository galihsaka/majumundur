package com.enigmacamp.majumundur.controller;
import com.enigmacamp.majumundur.dto.request.LoginRequest;
import com.enigmacamp.majumundur.dto.request.RegisterRequest;
import com.enigmacamp.majumundur.dto.response.CommonResponse;
import com.enigmacamp.majumundur.dto.response.LoginResponse;
import com.enigmacamp.majumundur.dto.response.RegisterResponse;
import com.enigmacamp.majumundur.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/majumundur/api/auth")
public class AuthController {
    private final AuthService authenticationService;

    @Autowired
    public AuthController(AuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authenticationService.register(request);
        CommonResponse<RegisterResponse> registerResponseCommonResponse=new CommonResponse<>();
        registerResponseCommonResponse.setStatusCode(HttpStatus.CREATED.value());
        registerResponseCommonResponse.setMessage("Account Registered Succesfully");
        registerResponseCommonResponse.setData(Optional.of(response));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerResponseCommonResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody LoginRequest authRequest){
        LoginResponse loginResponse=authenticationService.login(authRequest);
        CommonResponse<LoginResponse> loginResponseCommonResponse=new CommonResponse<>();
        loginResponseCommonResponse.setStatusCode(HttpStatus.OK.value());
        loginResponseCommonResponse.setMessage("Login Success");
        loginResponseCommonResponse.setData(Optional.of(loginResponse));
        return ResponseEntity.ok(loginResponseCommonResponse);
    }
}
