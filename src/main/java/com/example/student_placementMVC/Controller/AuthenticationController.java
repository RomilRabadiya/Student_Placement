package com.example.student_placementMVC.Controller;

import com.example.student_placementMVC.Security.Entity.Role;
import com.example.student_placementMVC.Security.Entity.Users;
import com.example.student_placementMVC.Security.Entity.Repo_Services.UsersService;
import com.example.student_placementMVC.Security.Entity.Repo_Services.RoleService;
import com.example.student_placementMVC.Services.CompanyService;
import com.example.student_placementMVC.company.company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class AuthenticationController {

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private UsersService UsersService;
    
    @Autowired
    private RoleService RoleService;
    
    
//  Below all endpoint Authentication NOT Needed
//  Below all endpoint Authentication NOT Needed
    
    
    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "Login_Register/LoginPage";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "Login_Register/access-denied";
    }
    
    
    
    @GetMapping("/")
    public String Home_Page(Model m,HttpSession session) 
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() ) 
        {

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String username = userDetails.getUsername();

            Users u = UsersService.findByUserId(username); 

            m.addAttribute("LoginUser", u);

            if ("STUDENT".equalsIgnoreCase(u.getRole().getRoleName())) 
            {
            	List<company> li=companyService.findAll();
            	m.addAttribute("companies", li);
                return "student-home";
            } 
            else if ("COMPANY".equalsIgnoreCase(u.getRole().getRoleName())) 
            {
                return "company-home";
            }
            return "admin-home";
        }

        return "redirect:/jobs/showMyLoginPage?error";
    }
    
    
    @GetMapping("/Register")
    String Student_Register(Model m)
    {	
    	m.addAttribute("User", new Users());
    	m.addAttribute("RolesOptions", Arrays.asList("STUDENT", "COMPANY"));
    	return "Login_Register/Register";
    }
    
    @PostMapping("/register-conformed")
    public String RegisterConformed(@ModelAttribute("User") Users u,Model m,HttpSession session) 
    {
    	Role r=u.getRole();
    	RoleService.getOrCreateRole(r.getRoleName());
    	
        u.setPassword("{noop}" + u.getPassword());
        
        try {
        	UsersService.save(u);
        } 
        catch (RuntimeException e) {
        	m.addAttribute("User", new Users());
        	m.addAttribute("RolesOptions", Arrays.asList("STUDENT", "COMPANY"));
        	m.addAttribute("error", "userId already Exist");
            return "Login_Register/Register";
        }
        
        session.setAttribute("User", u);
    	
    	if(r.getRoleName().equals("STUDENT"))
        {
    		return "redirect:/jobs/student-form";
        }
    	else if(r.getRoleName().equals("COMPANY"))
        {
    		return "redirect:/jobs/company-form";
        }
    	return "redirect:/jobs/admin-form";
    }
}