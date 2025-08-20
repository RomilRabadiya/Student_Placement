package com.example.student_placementMVC.ApplyRelation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRelationRepository extends JpaRepository<ApplyRelation, Long>
{
    boolean existsByCompanyIdAndStudentId(String companyId, String studentId);
    
    List<ApplyRelation> findByCompanyId(String companyId);
}
