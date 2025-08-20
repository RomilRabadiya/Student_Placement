package com.example.student_placementMVC.Controller.SupportClass;

import java.util.ArrayList;
import java.util.List;

public class tempcompanyRequiredSkill {
    private List<Pair<String, Double>> technicalSkills;
    private List<Pair<String, Double>> developmentSkills;
    private List<Pair<String, Double>> frameworkSkills;

    public tempcompanyRequiredSkill() {
        technicalSkills = new ArrayList<>();
        developmentSkills = new ArrayList<>();
        frameworkSkills = new ArrayList<>();
    }

	public List<Pair<String, Double>> getTechnicalSkills() {
		return technicalSkills;
	}

	public void setTechnicalSkills(List<Pair<String, Double>> technicalSkills) {
		this.technicalSkills = technicalSkills;
	}

	public List<Pair<String, Double>> getDevelopmentSkills() {
		return developmentSkills;
	}

	public void setDevelopmentSkills(List<Pair<String, Double>> developmentSkills) {
		this.developmentSkills = developmentSkills;
	}

	public List<Pair<String, Double>> getFrameworkSkills() {
		return frameworkSkills;
	}

	public void setFrameworkSkills(List<Pair<String, Double>> frameworkSkills) {
		this.frameworkSkills = frameworkSkills;
	}
    
    
}

