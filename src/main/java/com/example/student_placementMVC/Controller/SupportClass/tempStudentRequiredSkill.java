package com.example.student_placementMVC.Controller.SupportClass;

import java.util.ArrayList;
import java.util.List;

public class tempStudentRequiredSkill {
    private List<String> technicalSkills;
    private List<String> developmentSkills;
    private List<String> frameworkSkills;
    
    public tempStudentRequiredSkill() {
        technicalSkills = new ArrayList<>();
        developmentSkills = new ArrayList<>();
        frameworkSkills = new ArrayList<>();
    }

    // GETTERS AND SETTERS
    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public List<String> getDevelopmentSkills() {
        return developmentSkills;
    }

    public void setDevelopmentSkills(List<String> developmentSkills) {
        this.developmentSkills = developmentSkills;
    }

    public List<String> getFrameworkSkills() {
        return frameworkSkills;
    }

    public void setFrameworkSkills(List<String> frameworkSkills) {
        this.frameworkSkills = frameworkSkills;
    }
}
