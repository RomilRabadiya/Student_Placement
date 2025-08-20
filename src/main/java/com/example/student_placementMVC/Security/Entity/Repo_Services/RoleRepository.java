package com.example.student_placementMVC.Security.Entity.Repo_Services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_placementMVC.Security.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
