package com.example.student_placementMVC.requiredSkill;


import java.util.List;
import java.util.Optional;


public interface RequiredSkillDAO {
    void save(RequiredSkill skill);
    RequiredSkill findById(int id);
    List<RequiredSkill> findAll();
    void update(RequiredSkill skill);
    boolean deleteById(Long id);
}
