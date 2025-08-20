package com.example.student_placementMVC.company;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public class companyDAOImpl implements companyDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(company companyRequirement) {
        entityManager.persist(companyRequirement);
    }
    
    public company findById(String id) {
    	company requirement = entityManager.find(company.class, id);
        return requirement;
    }
    
    public List<company> findAll() {
        return entityManager.createQuery("FROM company", company.class)
                .getResultList();
    }

    @Transactional
    public void update(company companyRequirement) {
        entityManager.merge(companyRequirement);
    }
}