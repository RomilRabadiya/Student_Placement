package com.example.student_placementMVC.requiredSkill;


import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public class RequiredSkillDAOImpl implements RequiredSkillDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(RequiredSkill skill) {
        entityManager.persist(skill);
    }
    
    public RequiredSkill findById(int id) {
        RequiredSkill skill = entityManager.find(RequiredSkill.class, id);
        return skill;
    }
    
    public List<RequiredSkill> findAll() {
        return entityManager.createQuery("FROM RequiredSkill", RequiredSkill.class)
                .getResultList();
    }

    @Transactional
    public void update(RequiredSkill skill) {
        entityManager.merge(skill);
    }

    @Transactional
    public boolean deleteById(Long id) {
        RequiredSkill skill = entityManager.find(RequiredSkill.class, id);
        if (skill != null) {
            entityManager.remove(skill);
            return true;
        }
        return false;
    }
}
