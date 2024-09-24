package com.enigmacamp.majumundur.service.impl;

import com.enigmacamp.majumundur.entity.AppUser;
import com.enigmacamp.majumundur.entity.User;
import com.enigmacamp.majumundur.entity.Role;
import com.enigmacamp.majumundur.repository.UserRepository;
import com.enigmacamp.majumundur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid credential user"));
        List<Role.ERole> roleName=new ArrayList<>();
        for(int i=0;i<user.getRoles().size();i++){
            roleName.add(user.getRoles().get(i).getName());
        }
        AppUser appUser=new AppUser();
        appUser.setId(user.getId());
        appUser.setUsername(user.getUsername());
        appUser.setPassword(user.getPassword());
        appUser.setRole(roleName);
        return appUser;
    }
}
