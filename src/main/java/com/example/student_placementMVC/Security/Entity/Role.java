package com.example.student_placementMVC.Security.Entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Users> users = new HashSet<>();

    public Role() {}

    public Role(String roleName) 
    {
        this.roleName = roleName;
    }

    public String getRoleName() 
    {
        return roleName;
    }

    public void setRoleName(String roleName) 
    {
        this.roleName = roleName;
    }

    public Set<Users> getUsers() 
    {
        return users;
    }

    public void setUsers(Set<Users> users) 
    {
        this.users = users;
    }
}
