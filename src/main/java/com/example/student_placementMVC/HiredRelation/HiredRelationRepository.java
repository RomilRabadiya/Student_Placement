package com.example.student_placementMVC.HiredRelation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HiredRelationRepository extends JpaRepository<HiredRelation, Long>
{
	List<HiredRelation> findByStudentId(String studentId);
}
