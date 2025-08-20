package com.example.student_placementMVC.Controller;

import com.example.student_placementMVC.Controller.SupportClass.Pair;
import com.example.student_placementMVC.Controller.SupportClass.tempcompanyRequiredSkill;
import com.example.student_placementMVC.Security.Entity.Users;
import com.example.student_placementMVC.Services.CompanyService;
import com.example.student_placementMVC.company.company;
import com.example.student_placementMVC.requiredSkill.RequiredSkill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/jobs")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    
    
    //Company Part
    
    
    
    
    @RequestMapping("/company-form")
    public String showCompanyForm(Model model) 
    {
        model.addAttribute("company", new company());
        return "company/Company-Add-Form";
    }
    
    @PostMapping("/AddCompanyRequiredSkill")
    public String addRequiredSkillToCompany(
            @ModelAttribute("company") company company,
            Model model,
            HttpSession session) 
    {
    	
    	List<String> newtechnicalSkills = List.of("Java", "C++", "Python", "SQL");
        List<String> newdevelopmentSkills = List.of("Frontend", "Backend", "Fullstack");
        List<String> newframeworkSkills = List.of("Spring Boot", "Django", "React");

        
        
        tempcompanyRequiredSkill form = new tempcompanyRequiredSkill();

        form.setTechnicalSkills(
            newtechnicalSkills.stream()
                .map(skill -> Pair.of(skill, 0.0))
                .collect(Collectors.toList())
        );

        form.setDevelopmentSkills(
            newdevelopmentSkills.stream()
                .map(skill -> Pair.of(skill, 0.0))
                .collect(Collectors.toList())
        );

        form.setFrameworkSkills(
            newframeworkSkills.stream()
                .map(skill -> Pair.of(skill, 0.0))
                .collect(Collectors.toList())
        );

        
        
        model.addAttribute("companyRequiredSkill", form);

        model.addAttribute("company", company);
        
        session.setAttribute("company", company);

        return "RequiredSkill/RequiredSkill-Form-Company";
    }
    
    @PostMapping("/save-company-skills")
    public String save_skills_Company(
            @ModelAttribute("companyRequiredSkill") tempcompanyRequiredSkill TRS,
            Model model
            ,HttpSession session) 
    {
    	
    	company c=(company)session.getAttribute("company");
    	
    	if (c == null) 
    	{
            // Redirect back or show error page
            model.addAttribute("errorMessage", "Session expired. Please fill company details again.");
            return "redirect:/jobs/company-form";
        }

        Map<String, Double> MaptechnicalSkills = new HashMap<>();
        for (Pair<String,?> skill : TRS.getTechnicalSkills()) 
        {
            Double value = Double.parseDouble(skill.getValue().toString());
            MaptechnicalSkills.put(skill.getKey(), value);
        }

        Map<String, Double> MapdevelopmentSkills = new HashMap<>();
        for (Pair<String,?> skill : TRS.getDevelopmentSkills()) {
        	Double value = Double.parseDouble(skill.getValue().toString());
            MapdevelopmentSkills.put(skill.getKey(), value);
        }

        Map<String, Double> MapframeworkSkills = new HashMap<>();
        for (Pair<String,?> skill : TRS.getFrameworkSkills()) {
        	Double value = Double.parseDouble(skill.getValue().toString());
            MapframeworkSkills.put(skill.getKey(), value);
        }

        RequiredSkill rs = new RequiredSkill(MaptechnicalSkills, MapdevelopmentSkills, MapframeworkSkills);
        c.setRequiredSkills(rs);
        
        
    	Users u=(Users)session.getAttribute("User");
    	String username=u.getUserId();

        c.setId(username);
        System.out.println(username);
        companyService.save(c);
        model.addAttribute("company", c);
        session.removeAttribute("company");
        
        return "company/showCompany";
    }
}