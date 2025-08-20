package com.example.student_placementMVC.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_placementMVC.company.company;
import com.example.student_placementMVC.company.companyDAO;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    
    @Autowired
    private companyDAO companyRequireddaoimp;

    public void save(company companyRequirement) {
        companyRequireddaoimp.save(companyRequirement);
    }
    
    public company findById(String id) {
        return companyRequireddaoimp.findById(id);
    }
    
    public List<company> findAll() {
        return companyRequireddaoimp.findAll();
    }
    
    public void update(company companyRequirement) {
        companyRequireddaoimp.update(companyRequirement);
    }
}