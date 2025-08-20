package com.example.student_placementMVC.company;

import java.time.LocalDate;

import com.example.student_placementMVC.requiredSkill.RequiredSkill;

import jakarta.persistence.*;

@Entity
public class company 
{
    @Id
    @Column(name="company_id")
    private String id;

    private String name;
    private String jobRole;
    private double experienceRequired;
    private String location;
    private double minsalaryRange;
    private double maxsalaryRange;
    private LocalDate posting_date;
    private LocalDate application_deadline;

    @OneToOne(cascade = CascadeType.ALL)
    private RequiredSkill requiredSkills;

    public company() {}

    public company(String name, String jobRole, double experienceRequired,
                              String location, double minsalaryRange, double maxsalaryRange) 
    {
        this.name = name;
        this.jobRole = jobRole;
        this.experienceRequired = experienceRequired;
        this.location = location;
        this.minsalaryRange = minsalaryRange;
        this.maxsalaryRange = maxsalaryRange;
        this.posting_date = LocalDate.now();
        this.application_deadline = LocalDate.now().plusDays(30);
    }
    
    public void setRequiredSkills(RequiredSkill requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public RequiredSkill getRequiredSkills() {
        return requiredSkills;
    }

    @Override
    public String toString() 
    {
        return "CompanyRequirement [id=" + id + ", name=" + name + ", jobRole=" + jobRole + 
                ", requiredSkills=" + requiredSkills + ", experienceRequired=" + experienceRequired + 
                ", location=" + location + ", minsalaryRange=" + minsalaryRange + 
                ", maxsalaryRange=" + maxsalaryRange + ", posting_date=" + posting_date + 
                ", application_deadline=" + application_deadline + "]";
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public double getExperienceRequired() {
		return experienceRequired;
	}

	public void setExperienceRequired(double experienceRequired) {
		this.experienceRequired = experienceRequired;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getMinsalaryRange() {
		return minsalaryRange;
	}

	public void setMinsalaryRange(double minsalaryRange) {
		this.minsalaryRange = minsalaryRange;
	}

	public double getMaxsalaryRange() {
		return maxsalaryRange;
	}

	public void setMaxsalaryRange(double maxsalaryRange) {
		this.maxsalaryRange = maxsalaryRange;
	}

	public LocalDate getPosting_date() {
		return posting_date;
	}

	public void setPosting_date(LocalDate posting_date) {
		this.posting_date = posting_date;
	}

	public LocalDate getApplication_deadline() {
		return application_deadline;
	}

	public void setApplication_deadline(LocalDate application_deadline) {
		this.application_deadline = application_deadline;
	}
    
    
}
