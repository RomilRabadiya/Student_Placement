package com.example.student_placementMVC.requiredSkill;


import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "required_skill")
public class RequiredSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private int id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "technical_skills", joinColumns = @JoinColumn(name = "required_skill_id"))
    @MapKeyColumn(name = "technical_name")
    @Column(name = "technical_value")
    private Map<String, Double> technicalSkills = new HashMap<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "development_skills", joinColumns = @JoinColumn(name = "required_skill_id"))
    @MapKeyColumn(name = "development_name")
    @Column(name = "development_value")
    private Map<String, Double> developmentSkills = new HashMap<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "framework_skills", joinColumns = @JoinColumn(name = "required_skill_id"))
    @MapKeyColumn(name = "framework_name")
    @Column(name = "framework_value")
    private Map<String, Double> frameworkSkills = new HashMap<>();

    public RequiredSkill() {}

    public RequiredSkill(Map<String, Double> technicalSkills, Map<String, Double> developmentSkills, 
                         Map<String, Double> frameworkSkills) {
        this.technicalSkills = technicalSkills;
        this.developmentSkills = developmentSkills;
        this.frameworkSkills = frameworkSkills;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    
    public Map<String, Double> getTechnicalSkills() { return technicalSkills; }
    public void setTechnicalSkills(Map<String, Double> technicalSkills) { this.technicalSkills = technicalSkills; }

    public Map<String, Double> getDevelopmentSkills() { return developmentSkills; }
    public void setDevelopmentSkills(Map<String, Double> developmentSkills) { this.developmentSkills = developmentSkills; }

    public Map<String, Double> getFrameworkSkills() { return frameworkSkills; }
    public void setFrameworkSkills(Map<String, Double> frameworkSkills) { this.frameworkSkills = frameworkSkills; }

	
}
