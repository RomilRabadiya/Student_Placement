package com.example.student_placementMVC.HiredRelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HiredRelationService
{
    @Autowired
    private HiredRelationRepository repository;

    public HiredRelation save(HiredRelation relation)
    {
        return repository.save(relation);
    }

    public List<HiredRelation> findAll()
    {
        return repository.findAll();
    }

    public void deleteById(Long id)
    {
        repository.deleteById(id);
    }
    public List<String> getCompanyIdByStudentId(String studentId)
    {
        return repository.findByStudentId(studentId)
                         .stream()
                         .map(HiredRelation::getCompanyId)
                         .toList(); // returns empty list if none found
    }
}
