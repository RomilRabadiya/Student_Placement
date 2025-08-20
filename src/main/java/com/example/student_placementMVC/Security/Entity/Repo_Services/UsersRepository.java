package com.example.student_placementMVC.Security.Entity.Repo_Services;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_placementMVC.Security.Entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Users findByUserId(String userId);
    Users findByUserIdAndPassword(String userId, String password);
    boolean existsByUserId(String userId);
}
