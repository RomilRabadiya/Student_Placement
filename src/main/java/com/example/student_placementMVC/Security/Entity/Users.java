package com.example.student_placementMVC.Security.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    private String userId;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_name", referencedColumnName = "role_name")
    private Role role;

    public Users() {}

    public Users(String userId, String password, Role role) 
    {
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    public boolean hasRole(String roleName) 
    {
        return role != null && role.getRoleName().equalsIgnoreCase(roleName);
    }

    public String getUserId() 
    {
        return userId;
    }

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public Role getRole() 
    {
        return role;
    }

    public void setRole(Role role) 
    {
        this.role = role;
    }

    @Override
    public String toString() 
    {
        return "Users{" +
                "userId='" + userId + '\'' +
                ", role=" + (role != null ? role.getRoleName() : null) +
                '}';
    }
}
