package com.example.student_placementMVC.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_placementMVC.requiredSkill.RequiredSkill;
import com.example.student_placementMVC.requiredSkill.RequiredSkillDAOImpl;

import java.util.List;
import java.util.Optional;

@Service
public class RequiredSkillService {

    @Autowired
    private RequiredSkillDAOImpl requiredSkillDaoImpl;

    public void save(RequiredSkill skill) {
        requiredSkillDaoImpl.save(skill);
    }

    public RequiredSkill findById(int id) {
        return requiredSkillDaoImpl.findById(id);
    }

    public List<RequiredSkill> findAll() {
        return requiredSkillDaoImpl.findAll();
    }

    public void update(RequiredSkill skill) {
        requiredSkillDaoImpl.update(skill);
    }

    public boolean deleteById(Long id) {
        return requiredSkillDaoImpl.deleteById(id);
    }
}