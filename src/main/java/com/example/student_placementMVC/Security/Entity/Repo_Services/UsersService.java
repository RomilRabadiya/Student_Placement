package com.example.student_placementMVC.Security.Entity.Repo_Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.example.student_placementMVC.Security.Entity.Role;
import com.example.student_placementMVC.Security.Entity.Users;

@Service
@Transactional
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users save(Users user) {
    	if (usersRepository.existsByUserId(user.getUserId())) {
            throw new RuntimeException("User ID already exists. Please choose another one.");
        }
        return usersRepository.save(user);
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public void deleteUser(String userId) {
        usersRepository.deleteById(userId);
    }

    public Users findByUserId(String userId) {
        return usersRepository.findByUserId(userId);
    }
    
    public Role getRoleByUserIdAndPassword(String userId, String password) {
        Users user = usersRepository.findByUserIdAndPassword(userId, password);
        return (user != null) ? user.getRole() : null;
    }
}
