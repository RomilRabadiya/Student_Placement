package com.example.student_placementMVC.Student;

import java.util.List;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class StudentDAOImpl implements StudentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Student s) {
        entityManager.persist(s);
    }

    public Student findById(String studentId) {
        return entityManager.find(Student.class, studentId);
    }

    public List<String> allStudentIds() {
        return entityManager.createNativeQuery("SELECT student_id FROM student")
                .getResultList()
                .stream()
                .map(Object::toString)
                .toList();
    }

    public List<Student> findAll() {
        return entityManager.createQuery("FROM Student", Student.class)
                .getResultList();
    }

    @Transactional
    public Student update(Student s) {
        return entityManager.merge(s);
    }
}
