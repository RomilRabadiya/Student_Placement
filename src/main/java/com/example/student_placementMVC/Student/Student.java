package com.example.student_placementMVC.Student;


import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.student_placementMVC.requiredSkill.RequiredSkill;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name="student_id")
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String department;
    
    @Column(name = "placement_status")
    private String placementStatus;
    
    @Column(name = "cgpa")
    private Double cgpa;
    
    private String githubUrl;
    private String linkedinProfile;
    private String certificates;
    
    @OneToOne(cascade = CascadeType.ALL)
    private RequiredSkill requiredSkills;

    @Column(name = "resume_url")
    private String resumeUrl;

    private LocalDate enrollmentDate;
    private LocalDate graduationDate;

    public Student() {}

    public Student(String firstName, String lastName, String email, String department, 
                   String placementStatus, Double cgpa, String githubUrl, 
                   String linkedinProfile, String certificates) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.placementStatus = placementStatus;
        this.cgpa = cgpa;
        this.githubUrl = githubUrl;
        this.linkedinProfile = linkedinProfile;
        this.certificates = certificates;
        this.enrollmentDate = LocalDate.now();
    }

    public void setRequiredSkills(RequiredSkill requiredSkills) 
    {
        this.requiredSkills = requiredSkills;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPlacementStatus() { return placementStatus; }
    public void setPlacementStatus(String placementStatus) { this.placementStatus = placementStatus; }

    public Double getCgpa() { return cgpa; }
    public void setCgpa(Double cgpa) { this.cgpa = cgpa; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getLinkedinProfile() { return linkedinProfile; }
    public void setLinkedinProfile(String linkedinProfile) { this.linkedinProfile = linkedinProfile; }

    public String getCertificates() { return certificates; }
    public void setCertificates(String certificates) { this.certificates = certificates; }

    public RequiredSkill getRequiredSkills() { return requiredSkills; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public LocalDate getGraduationDate() { return graduationDate; }
    public void setGraduationDate(LocalDate graduationDate) { this.graduationDate = graduationDate; }
}
