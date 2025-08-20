package com.example.student_placementMVC.Controller;

import com.example.student_placementMVC.ApplyRelation.ApplyRelation;
import com.example.student_placementMVC.ApplyRelation.ApplyRelationService;
import com.example.student_placementMVC.Controller.SupportClass.tempStudentRequiredSkill;
import com.example.student_placementMVC.HiredRelation.HiredRelationService;
import com.example.student_placementMVC.Security.Entity.Users;
import com.example.student_placementMVC.Services.CompanyService;
import com.example.student_placementMVC.Services.StudentService;
import com.example.student_placementMVC.Student.Student;
import com.example.student_placementMVC.company.company;
import com.example.student_placementMVC.requiredSkill.RequiredSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jobs")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private com.example.student_placementMVC.Services.RequiredSkillService RequiredSkillService;
    
    @Autowired
    private ApplyRelationService ApplyRelationS;
    
    @Autowired
    private HiredRelationService HiredRelationS;
    
    
    @PostMapping("/applyForCompany/{id}")
    public String applyForCompany(@PathVariable String id,Model m) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() ) 
        {
        	String username;

            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString(); // principal is already the username
            }
            
			if(ApplyRelationS.isCompanyStudentCombinationExists(id,username))
			{
				return "student-already-present";
			}
			
			ApplyRelation a=new ApplyRelation(id,username);
			
			ApplyRelationS.save(a);
        }
        company c=companyService.findById(id);
        System.out.println(c);
        m.addAttribute("company", c);
        return "company/companyDetail";
    }

    
    @GetMapping("offered_company")
    public String offered_company(Model m)
    {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() ) 
        {

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String username = userDetails.getUsername();
            List<String> companyIds=HiredRelationS.getCompanyIdByStudentId(username);
            List<company> companies=new ArrayList();
            Map<String, Boolean> flag = new HashMap<>();
            for(String i:companyIds)
            {
            	if(flag.containsKey(i)==false)
            	{
            		companies.add(companyService.findById(i));
            		flag.put(i, true);
            	}
            }
            m.addAttribute("companies",companies);
            return "company/showAllCompany";
        }
        
        return "redirect:/jobs/access-denied";

    }
    

    @GetMapping("/student-form")
    public String showStudentForm(Model model) 
    {
        model.addAttribute("student", new Student());
        return "Student/Student-Add-Form";
    }
    
    
    @PostMapping("/addRequiredSkill")
    public String addRequiredSkillToStudent(
            @ModelAttribute("student") Student student,
            Model model,
            HttpSession session) 
    {
    	
        model.addAttribute("newtechnicalSkills", List.of("Java", "C++", "Python", "SQL"));
        model.addAttribute("newdevelopmentSkills", List.of("Frontend", "Backend", "Fullstack"));
        model.addAttribute("newframeworkSkills", List.of("Spring Boot", "Django", "React"));
        model.addAttribute("studentReqiuredSkill", new tempStudentRequiredSkill());


        model.addAttribute("student", student);
        
        session.setAttribute("student", student);

        return "RequiredSkill/RequiredSkill-Form-Student";
    }
    
    
    
    @PostMapping("/save-skills")
    public String save_skills_Student(
            @ModelAttribute("studentReqiuredSkill") tempStudentRequiredSkill TRS,
            Model model
            ,HttpSession session) 
    {
    	
    	Student s=(Student)session.getAttribute("student");

        Map<String, Double> MaptechnicalSkills = new HashMap<>();
        for (String skill : TRS.getTechnicalSkills()) {
            MaptechnicalSkills.put(skill, 1.0);
        }

        Map<String, Double> MapdevelopmentSkills = new HashMap<>();
        for (String skill : TRS.getDevelopmentSkills()) {
            MapdevelopmentSkills.put(skill, 1.0);
        }

        Map<String, Double> MapframeworkSkills = new HashMap<>();
        for (String skill : TRS.getFrameworkSkills()) {
            MapframeworkSkills.put(skill, 1.0);
        }

        RequiredSkill rs = new RequiredSkill(MaptechnicalSkills, MapdevelopmentSkills, MapframeworkSkills);
        s.setRequiredSkills(rs);

        Users u=(Users)session.getAttribute("User");
    	String username=u.getUserId();

        s.setId(username);
        studentService.save(s);
        model.addAttribute("student", s);

        session.removeAttribute("student");
        return "Student/showStudent";
    }
}