package com.example.student_placementMVC.Security.Entity.Repo_Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_placementMVC.Security.Entity.Role;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getOrCreateRole(String roleName) 
    {
        return roleRepository.findById(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}

