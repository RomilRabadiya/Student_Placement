package com.example.student_placementMVC.Student;
import java.util.List;

public interface StudentDAO {
    void save(Student s);
    Student findById(String studentId);
    List<Student> findAll();
    List<String> allStudentIds();
    Student update(Student s);
}
