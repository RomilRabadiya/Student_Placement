package com.example.student_placementMVC.company;


import java.util.*;

public interface companyDAO {
    void save(company companyRequirement);
    
    company findById(String id);
    
    List<company> findAll();
    
    void update(company companyRequirement);
}
