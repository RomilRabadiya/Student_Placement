package com.example.student_placementMVC.Controller;

import com.example.student_placementMVC.ApplyRelation.ApplyRelationService;
import com.example.student_placementMVC.Controller.SupportClass.result_pair;
import com.example.student_placementMVC.HiredRelation.HiredRelation;
import com.example.student_placementMVC.HiredRelation.HiredRelationService;
import com.example.student_placementMVC.Security.Entity.Users;
import com.example.student_placementMVC.Security.Entity.Repo_Services.UsersService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jobs")
public class ATSController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private ApplyRelationService ApplyRelationS;
    
    @Autowired
    private HiredRelationService HiredRelationS;
    
    @Autowired
    private UsersService UsersService;
    
    
    
    
    
    
    
//    Below all endpoint Authentication Needed
//    Below all endpoint Authentication Needed
    
    @GetMapping("/Generate_ATS_Score")
    public String sortingAccordingReuiredSkill(Model m)
    {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() ) 
        {

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String username = userDetails.getUsername();
    	
            System.out.println(username);
            
	    	List<result_pair> result = new ArrayList();
	    	
	    	List<Double> ats_score=new ArrayList();
	      	
	      	company cr=companyService.findById(username);
	      	
	      	if(cr==null)
	      	{
	      		m.addAttribute("Error","Id Not Found");
	      		return "sortingAccording_ATS_Score/errorPage";
	      	}
	      	
	      	
	      	RequiredSkill comapny_RequiredSkill=cr.getRequiredSkills();
	      	
	      	
	      	
	      	List<String> studentIds=ApplyRelationS.getStudentIdsByCompanyId(username);
	      	
	      	
	      	
	      	if(studentIds==null)
	      	{
	      		m.addAttribute("Error", "Student Not Found");
	      		return "sortingAccording_ATS_Score/errorPage";
	      	}
	      	
	      	for(String studentId:studentIds)
	      	{
	      		Student st=studentService.findById(studentId);
	      		if (st == null) {
	      	        // Handle or skip this null student case, e.g., continue to next student
	      	        continue;
	      	    }
	      		RequiredSkill student_RequiredSkill=st.getRequiredSkills();
	      		ats_score.add(compareSkills(student_RequiredSkill,comapny_RequiredSkill));
	      	}
	      	
	      	for(int i=0;i<ats_score.size();i++)
	      	{
	      		result_pair temp=new result_pair();
	      		temp.st = studentService.findById(studentIds.get(i));
	      	    temp.ats_score = ats_score.get(i);
	      		result.add(temp);
	      	}
	      	
	      	m.addAttribute("Result",result );

      	
	      	return "sortingAccording_ATS_Score/showResult";
        }
        
        return "redirect:/jobs/access-denied";
    }
    
    
    
    @PostMapping("/submit")
    public String handleSelectedIds(@RequestParam(name = "selectedIds", required = false) List<String> selectedIds
    		,Model m
    		,HttpSession session) 
    {
    	if(selectedIds==null || selectedIds.isEmpty())
    	{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null && auth.isAuthenticated() ) 
            {

                UserDetails userDetails = (UserDetails) auth.getPrincipal();
                String username = userDetails.getUsername();

                Users u = UsersService.findByUserId(username); 

                m.addAttribute("LoginUser", u);
                return "company-home";
            }
            
            return "redirect:/jobs/access-denied";
    	}
    	
    	
        List<Student> SelectedStudent=new ArrayList();
    	if (!selectedIds.isEmpty()) 
        {
            for(String i:selectedIds)
            {
            	SelectedStudent.add(studentService.findById(i));
            }
        } 
        else {
            m.addAttribute("Error", "No students selected.");
            return "sortingAccording_ATS_Score/errorPage";
        }

    	m.addAttribute("SelectedStudent", SelectedStudent);
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if (auth != null && auth.isAuthenticated() ) 
        {
        	String username;

            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            
            for(String st:selectedIds)
            {
            	
            	HiredRelationS.save(new HiredRelation(username,st));
            }
            
            
        }
    	return "sortingAccording_ATS_Score/ShowSelectedStudent";
    }

    
    
    
  
	  private double compareSkills(RequiredSkill studentSkills, RequiredSkill jobSkills) 
	  {
	  	Map<String, Double> studenttechnicalSkills=studentSkills.getTechnicalSkills();
	  	List<String> studenttechnicalSkillsList=new ArrayList();
	  	for (Map.Entry<String, Double> entry : studenttechnicalSkills.entrySet())
	  	{
	  	    String skill = entry.getKey();
	  	    studenttechnicalSkillsList.add(skill);
	  	}
	  	
	  	Map<String, Double> requiredtechnicalSkills=jobSkills.getTechnicalSkills();
	  	
	  	double ats_technicalSkills=comparetechnicalSkills(studenttechnicalSkillsList,requiredtechnicalSkills);
	  	
	  	
	  	
	  	Map<String, Double> studentDevelopmentSkills = studentSkills.getDevelopmentSkills();
	  	List<String> studentDevelopmentSkillsList = new ArrayList<>();
	  	for (Map.Entry<String, Double> entry : studentDevelopmentSkills.entrySet())
	  	{
	  	    String skill = entry.getKey();
	  	    studentDevelopmentSkillsList.add(skill);
	  	}
	  	Map<String, Double> requiredDevelopmentSkills = jobSkills.getDevelopmentSkills();
	  	double ats_developmentSkills = compareDevelopmentSkills(studentDevelopmentSkillsList, requiredDevelopmentSkills);

	  	
	  	
	  	Map<String, Double> studentFrameworkSkills = studentSkills.getFrameworkSkills();
	  	List<String> studentFrameworkSkillsList = new ArrayList<>();
	  	for (Map.Entry<String, Double> entry : studentFrameworkSkills.entrySet())
	  	{
	  	    String skill = entry.getKey();
	  	    studentFrameworkSkillsList.add(skill);
	  	}
	  	Map<String, Double> requiredFrameworkSkills = jobSkills.getFrameworkSkills();
	  	double ats_frameworkSkills = compareFrameworkSkills(studentFrameworkSkillsList, requiredFrameworkSkills);
	  	
	  	return ats_technicalSkills+ats_developmentSkills+ats_frameworkSkills;
	  }
	  
	  private double comparetechnicalSkills(List<String> studenttechnicalSkills,Map<String, Double> requiredtechnicalSkills)
	  {
	  	double total=0;
	  	for(String skill:studenttechnicalSkills)
	  	{
	  		total+=requiredtechnicalSkills.get(skill);
	  	}
	  	
	  	return total;
	  }
	  
	  private double compareDevelopmentSkills(List<String> studentDevelopmentSkills, Map<String, Double> requiredDevelopmentSkills)
	  {
	      double total = 0;
	      for (String skill : studentDevelopmentSkills)
	      {
	          total += requiredDevelopmentSkills.get(skill);
	      }
	      return total;
	  }

	  private double compareFrameworkSkills(List<String> studentFrameworkSkills, Map<String, Double> requiredFrameworkSkills)
	  {
	      double total = 0;
	      for (String skill : studentFrameworkSkills)
	      {
	          total += requiredFrameworkSkills.get(skill);
	      }
	      return total;
	  }
}