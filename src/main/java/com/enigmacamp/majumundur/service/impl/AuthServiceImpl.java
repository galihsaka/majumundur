package com.enigmacamp.majumundur.service.impl;

import com.enigmacamp.majumundur.dto.request.LoginRequest;
import com.enigmacamp.majumundur.dto.request.RegisterRequest;
import com.enigmacamp.majumundur.dto.response.LoginResponse;
import com.enigmacamp.majumundur.dto.response.RegisterResponse;
import com.enigmacamp.majumundur.entity.AppUser;
import com.enigmacamp.majumundur.entity.Role;
import com.enigmacamp.majumundur.entity.User;
import com.enigmacamp.majumundur.repository.UserRepository;
import com.enigmacamp.majumundur.security.JwtUtil;
import com.enigmacamp.majumundur.service.AuthService;
import com.enigmacamp.majumundur.service.RoleService;
import com.enigmacamp.majumundur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleService roleService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private UserService userService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RoleService roleService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        List<Role.ERole> storeRole=request.getRoles();
        List<Role> roles=roleService.getOrSave(storeRole);
        User user=new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);
        user.setFullName(request.getFullName());
        user.setGender(request.getGender());
        User saveUser=userRepository.save(user);
        RegisterResponse registerResponse=new RegisterResponse();
        registerResponse.setUsername(saveUser.getUsername());
        registerResponse.setRoles(saveUser.getRoles());
        registerResponse.setFullName(saveUser.getFullName());
        registerResponse.setGender(saveUser.getGender());
        registerResponse.setId(saveUser.getId());
        return registerResponse;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        request.getPassword(),
                        userDetails.getAuthorities()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.createToken(appUser);
        LoginResponse loginResponse=new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(appUser.getRole());
        loginResponse.setUsername(appUser.getUsername());
        return loginResponse;
    }
}
