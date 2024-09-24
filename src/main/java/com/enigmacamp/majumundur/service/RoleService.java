package com.enigmacamp.majumundur.service;

import com.enigmacamp.majumundur.entity.Role;

import java.util.List;

public interface RoleService {
    public List<Role> getOrSave(List<Role.ERole> roles);
}
