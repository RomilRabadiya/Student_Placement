package com.example.student_placementMVC.ApplyRelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplyRelationService
{
    @Autowired
    private ApplyRelationRepository repository;

    public ApplyRelation save(ApplyRelation relation)
    {
        return repository.save(relation);
    }

    public List<ApplyRelation> findAll()
    {
        return repository.findAll();
    }

    public void deleteById(Long id)
    {
        repository.deleteById(id);
    }
    
    public boolean isCompanyStudentCombinationExists(String companyId, String studentId)
    {
        return repository.existsByCompanyIdAndStudentId(companyId, studentId);
    }
    
    public List<String> getStudentIdsByCompanyId(String companyId) {
        List<ApplyRelation> relations = repository.findByCompanyId(companyId);
        List<String> studentIds = new ArrayList<>();
        for (ApplyRelation relation : relations) {
            studentIds.add(relation.getStudentId());
        }
        return studentIds;
    }
}
