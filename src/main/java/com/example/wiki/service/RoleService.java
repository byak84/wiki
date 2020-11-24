package com.example.wiki.service;

import com.example.wiki.dao.RoleRepo;
import com.example.wiki.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RoleService {
    RoleRepo roleRepo;
    @Autowired
    public void setRoleRepo(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
//    ------------------------

    public Optional<Role> getRoleByName(String roleName) {
        return Optional.ofNullable(roleRepo.findByName(roleName));
    }
}
