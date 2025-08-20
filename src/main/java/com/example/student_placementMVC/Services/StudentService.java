package com.example.student_placementMVC.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_placementMVC.Student.Student;
import com.example.student_placementMVC.Student.StudentDAOImpl;

import java.util.List;

@Service
public class StudentService
{
    @Autowired
    private StudentDAOImpl studentDaoImpl; // fixed spelling from 'Imp' to 'Impl'

    public void save(Student student)
    {
        studentDaoImpl.save(student);
    }

    public Student findById(String studentId)
    {
        return studentDaoImpl.findById(studentId);
    }

    public List<Student> findAll()
    {
        return studentDaoImpl.findAll();
    }
    
    public Student update(Student student)
    {
        return studentDaoImpl.update(student);
    }
    
    public List<String> getAllStudentIds()
    {
        return studentDaoImpl.allStudentIds();
    }
}
