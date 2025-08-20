//package com.example.student_placementMVC.Controller;
//
//
//import com.example.student_placementMVC.ApplyRelation.ApplyRelation;
//import com.example.student_placementMVC.ApplyRelation.ApplyRelationService;
//import com.example.student_placementMVC.Controller.SupportClass.Pair;
//import com.example.student_placementMVC.Controller.SupportClass.result_pair;
//import com.example.student_placementMVC.Controller.SupportClass.tempStudentRequiredSkill;
//import com.example.student_placementMVC.Controller.SupportClass.tempcompanyRequiredSkill;
//import com.example.student_placementMVC.HiredRelation.HiredRelation;
//import com.example.student_placementMVC.HiredRelation.HiredRelationService;
//import com.example.student_placementMVC.Security.Entity.Role;
//import com.example.student_placementMVC.Security.Entity.Users;
//import com.example.student_placementMVC.Security.Entity.Repo_Services.UsersService;
//import com.example.student_placementMVC.Security.Entity.Repo_Services.RoleService;
//import com.example.student_placementMVC.Services.CompanyService;
//import com.example.student_placementMVC.Services.StudentService;
//import com.example.student_placementMVC.Student.Student;
//import com.example.student_placementMVC.company.company;
//import com.example.student_placementMVC.requiredSkill.RequiredSkill;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import jakarta.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/jobs")
//public class JobApplication {
//
//    @Autowired
//    private StudentService studentService;
//
//    @Autowired
//    private CompanyService companyService;
//
//    @Autowired
//    private com.example.student_placementMVC.Services.RequiredSkillService RequiredSkillService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    
//    @Autowired
//    private UsersService UsersService;
//    
//    @Autowired
//    private RoleService RoleService;
//    
//    @Autowired
//    private ApplyRelationService ApplyRelationS;
//    
//    @Autowired
//    private HiredRelationService HiredRelationS;
//    
//    
////  Below all endpoint Authentication NOT Needed
////  Below all endpoint Authentication NOT Needed
//    
//    
//    @GetMapping("/showMyLoginPage")
//    public String showMyLoginPage() {
//        return "Login_Register/LoginPage";
//    }
//
//    @GetMapping("/access-denied")
//    public String showAccessDeniedPage() {
//        return "Login_Register/access-denied";
//    }
//    
//    
//    
//    @GetMapping("/")
//    public String Home_Page(Model m,HttpSession session) 
//    {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null && auth.isAuthenticated() ) 
//        {
//
//            UserDetails userDetails = (UserDetails) auth.getPrincipal();
//            String username = userDetails.getUsername();
//
//            Users u = UsersService.findByUserId(username); 
//
//            m.addAttribute("LoginUser", u);
//
//            if ("STUDENT".equalsIgnoreCase(u.getRole().getRoleName())) 
//            {
//            	List<company> li=companyService.findAll();
//            	m.addAttribute("companies", li);
//                return "student-home";
//            } 
//            else if ("COMPANY".equalsIgnoreCase(u.getRole().getRoleName())) 
//            {
//                return "company-home";
//            }
//            return "admin-home";
//        }
//
//        return "redirect:/jobs/showMyLoginPage?error";
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    @PostMapping("/applyForCompany/{id}")
//    public String applyForCompany(@PathVariable String id,Model m) {
//    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null && auth.isAuthenticated() ) 
//        {
//        	String username;
//
//            Object principal = auth.getPrincipal();
//            if (principal instanceof UserDetails) {
//                username = ((UserDetails) principal).getUsername();
//            } else {
//                username = principal.toString(); // principal is already the username
//            }
//            
//			if(ApplyRelationS.isCompanyStudentCombinationExists(id,username))
//			{
//				return "student-already-present";
//			}
//			
//			ApplyRelation a=new ApplyRelation(id,username);
//			
//			ApplyRelationS.save(a);
//        }
//        company c=companyService.findById(id);
//        System.out.println(c);
//        m.addAttribute("company", c);
//        return "company/companyDetail";
//    }
//
//
//    
//    @GetMapping("/Register")
//    String Student_Register(Model m)
//    {	
//    	m.addAttribute("User", new Users());
//    	m.addAttribute("RolesOptions", Arrays.asList("STUDENT", "COMPANY", "ADMIN"));
//    	return "Login_Register/Register";
//    }
//    
//    @PostMapping("/register-conformed")
//    public String RegisterConformed(@ModelAttribute("User") Users u) 
//    {
//    	Role r=u.getRole();
//    	RoleService.getOrCreateRole(r.getRoleName());
//    	
//        u.setPassword("{noop}" + u.getPassword());
//    	
//    	UsersService.save(u);
//    	
//    	if(r.getRoleName().equals("STUDENT"))
//        {
//    		return "redirect:/jobs/student-form";
//        }
//    	else if(r.getRoleName().equals("COMPANY"))
//        {
//    		return "redirect:/jobs/company-form";
//        }
//    	return "redirect:/jobs/admin-form";
//    }
//    
//    @GetMapping("/admin-form")
//    public String f()
//    {
//    	return "admin/admin-form";
//    }
//
//    
//    @GetMapping("/student-form")
//    public String showStudentForm(Model model) 
//    {
//        model.addAttribute("student", new Student());
//        return "Student/Student-Add-Form";
//    }
//    
//    @GetMapping("offered_company")
//    public String offered_company(Model m)
//    {
//    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null && auth.isAuthenticated() ) 
//        {
//
//            UserDetails userDetails = (UserDetails) auth.getPrincipal();
//            String username = userDetails.getUsername();
//            List<String> companyIds=HiredRelationS.getCompanyIdByStudentId(username);
//            List<company> companies=new ArrayList();
//            for(String i:companyIds)
//            {
//            	System.out.println(i);
//            	System.out.println();
//            	System.out.println();
//            	companies.add(companyService.findById(i));
//            }
//            m.addAttribute("companies",companies);
//            return "company/showAllCompany";
//        }
//        
//        return "redirect:/jobs/access-denied";
//
//    }
//    
//
//    @PostMapping("/addRequiredSkill")
//    public String addRequiredSkillToStudent(
//            @ModelAttribute("student") Student student,
//            Model model,
//            HttpSession session) 
//    {
//    	
//        model.addAttribute("newtechnicalSkills", List.of("Java", "C++", "Python", "SQL"));
//        model.addAttribute("newdevelopmentSkills", List.of("Frontend", "Backend", "Fullstack"));
//        model.addAttribute("newframeworkSkills", List.of("Spring Boot", "Django", "React"));
//        model.addAttribute("studentReqiuredSkill", new tempStudentRequiredSkill());
//
//
//        model.addAttribute("student", student);
//        
//        session.setAttribute("student", student);
//
//        return "RequiredSkill/RequiredSkill-Form-Student";
//    }
//    
//    
//    
//    @PostMapping("/save-skills")
//    public String save_skills_Student(
//            @ModelAttribute("studentReqiuredSkill") tempStudentRequiredSkill TRS,
//            Model model
//            ,HttpSession session) 
//    {
//    	
//    	Student s=(Student)session.getAttribute("student");
//
//        Map<String, Double> MaptechnicalSkills = new HashMap<>();
//        for (String skill : TRS.getTechnicalSkills()) {
//            MaptechnicalSkills.put(skill, 1.0);
//        }
//
//        Map<String, Double> MapdevelopmentSkills = new HashMap<>();
//        for (String skill : TRS.getDevelopmentSkills()) {
//            MapdevelopmentSkills.put(skill, 1.0);
//        }
//
//        Map<String, Double> MapframeworkSkills = new HashMap<>();
//        for (String skill : TRS.getFrameworkSkills()) {
//            MapframeworkSkills.put(skill, 1.0);
//        }
//
//        RequiredSkill rs = new RequiredSkill(MaptechnicalSkills, MapdevelopmentSkills, MapframeworkSkills);
//        s.setRequiredSkills(rs);
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null && auth.isAuthenticated() ) 
//        {
//        	String username;
//
//            Object principal = auth.getPrincipal();
//            if (principal instanceof UserDetails) {
//                username = ((UserDetails) principal).getUsername();
//            } else {
//                username = principal.toString(); // principal is already the username
//            }
//
//            s.setId(username);
//            studentService.save(s);
//            model.addAttribute("student", s);
//
//            session.removeAttribute("student");
//            return "Student/showStudent";
//        }
//
//        return "redirect:/jobs/access-denied";
//    }
//    
//    
//    
//    //Company Part
//    
//    
//    
//    
//    @RequestMapping("/company-form")
//    public String showCompanyForm(Model model) 
//    {
//        model.addAttribute("company", new company());
//        return "company/Company-Add-Form";
//    }
//    
//    @PostMapping("/AddCompanyRequiredSkill")
//    public String addRequiredSkillToCompany(
//            @ModelAttribute("company") company company,
//            Model model,
//            HttpSession session) 
//    {
//    	
//    	List<String> newtechnicalSkills = List.of("Java", "C++", "Python", "SQL");
//        List<String> newdevelopmentSkills = List.of("Frontend", "Backend", "Fullstack");
//        List<String> newframeworkSkills = List.of("Spring Boot", "Django", "React");
//
//        
//        
//        tempcompanyRequiredSkill form = new tempcompanyRequiredSkill();
//
//        form.setTechnicalSkills(
//            newtechnicalSkills.stream()
//                .map(skill -> Pair.of(skill, 0.0))
//                .collect(Collectors.toList())
//        );
//
//        form.setDevelopmentSkills(
//            newdevelopmentSkills.stream()
//                .map(skill -> Pair.of(skill, 0.0))
//                .collect(Collectors.toList())
//        );
//
//        form.setFrameworkSkills(
//            newframeworkSkills.stream()
//                .map(skill -> Pair.of(skill, 0.0))
//                .collect(Collectors.toList())
//        );
//
//        
//        
//        model.addAttribute("companyRequiredSkill", form);
//
//        model.addAttribute("company", company);
//        
//        session.setAttribute("company", company);
//
//        return "RequiredSkill/RequiredSkill-Form-Company";
//    }
//    
//    @PostMapping("/save-company-skills")
//    public String save_skills_Company(
//            @ModelAttribute("companyRequiredSkill") tempcompanyRequiredSkill TRS,
//            Model model
//            ,HttpSession session) 
//    {
//    	
//    	company c=(company)session.getAttribute("company");
//    	
//    	if (c == null) 
//    	{
//            // Redirect back or show error page
//            model.addAttribute("errorMessage", "Session expired. Please fill company details again.");
//            return "redirect:/jobs/company-form";
//        }
//
//        Map<String, Double> MaptechnicalSkills = new HashMap<>();
//        for (Pair<String,?> skill : TRS.getTechnicalSkills()) 
//        {
//            Double value = Double.parseDouble(skill.getValue().toString());
//            System.out.println(skill.getKey()+" "+value);
//            MaptechnicalSkills.put(skill.getKey(), value);
//        }
//
//        Map<String, Double> MapdevelopmentSkills = new HashMap<>();
//        for (Pair<String,?> skill : TRS.getDevelopmentSkills()) {
//        	Double value = Double.parseDouble(skill.getValue().toString());
//        	System.out.println(skill.getKey()+" "+value);
//            MapdevelopmentSkills.put(skill.getKey(), value);
//        }
//
//        Map<String, Double> MapframeworkSkills = new HashMap<>();
//        for (Pair<String,?> skill : TRS.getFrameworkSkills()) {
//        	Double value = Double.parseDouble(skill.getValue().toString());
//        	System.out.println(skill.getKey()+" "+value);
//            MapframeworkSkills.put(skill.getKey(), value);
//        }
//
//        RequiredSkill rs = new RequiredSkill(MaptechnicalSkills, MapdevelopmentSkills, MapframeworkSkills);
//        c.setRequiredSkills(rs);
//        
//        
//        
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null && auth.isAuthenticated() ) 
//        {
//
//            UserDetails userDetails = (UserDetails) auth.getPrincipal();
//            String username = userDetails.getUsername();
//            c.setId(username);
//		    companyService.save(c);
//		    model.addAttribute("company", c);
//		    session.removeAttribute("company");
//        }
//
//        return "company/showCompany";
//    }
//    
//    
//    
//    
//    
//    
//    
////    Below all endpoint Authentication Needed
////    Below all endpoint Authentication Needed
//    
//    @GetMapping("/Generate_ATS_Score")
//    public String sortingAccordingReuiredSkill(Model m)
//    {
//    	
//    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null && auth.isAuthenticated() ) 
//        {
//
//            UserDetails userDetails = (UserDetails) auth.getPrincipal();
//            String username = userDetails.getUsername();
//    	
//            System.out.println(username);
//            
//	    	List<result_pair> result = new ArrayList();
//	    	
//	    	List<Double> ats_score=new ArrayList();
//	      	
//	      	company cr=companyService.findById(username);
//	      	
//	      	if(cr==null)
//	      	{
//	      		m.addAttribute("Error","Id Not Found");
//	      		return "sortingAccording_ATS_Score/errorPage";
//	      	}
//	      	
//	      	
//	      	RequiredSkill comapny_RequiredSkill=cr.getRequiredSkills();
//	      	
//	      	
//	      	
//	      	List<String> studentIds=ApplyRelationS.getStudentIdsByCompanyId(username);
//	      	
//	      	
//	      	
//	      	if(studentIds==null)
//	      	{
//	      		m.addAttribute("Error", "Student Not Found");
//	      		return "sortingAccording_ATS_Score/errorPage";
//	      	}
//	      	
//	      	for(String studentId:studentIds)
//	      	{
//	      		Student st=studentService.findById(studentId);
//	      		if (st == null) {
//	      	        // Handle or skip this null student case, e.g., continue to next student
//	      	        continue;
//	      	    }
//	      		RequiredSkill student_RequiredSkill=st.getRequiredSkills();
//	      		ats_score.add(compareSkills(student_RequiredSkill,comapny_RequiredSkill));
//	      	}
//	      	
//	      	for(int i=0;i<ats_score.size();i++)
//	      	{
//	      		result_pair temp=new result_pair();
//	      		temp.st = studentService.findById(studentIds.get(i));
//	      	    temp.ats_score = ats_score.get(i);
//	      		result.add(temp);
//	      	}
//	      	
//	      	m.addAttribute("Result",result );
//
//      	
//	      	return "sortingAccording_ATS_Score/showResult";
//        }
//        
//        return "redirect:/jobs/access-denied";
//    }
//    
//    
//    
//    @PostMapping("/submit")
//    public String handleSelectedIds(@RequestParam(name = "selectedIds", required = false) List<String> selectedIds
//    		,Model m
//    		,HttpSession session) {
//        List<Student> SelectedStudent=new ArrayList();
//    	if (!selectedIds.isEmpty()) 
//        {
//            for(String i:selectedIds)
//            {
//            	SelectedStudent.add(studentService.findById(i));
//            }
//        } 
//        else {
//            m.addAttribute("Error", "No students selected.");
//            return "sortingAccording_ATS_Score/errorPage";
//        }
//
//    	m.addAttribute("SelectedStudent", SelectedStudent);
//    	
//    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    	if (auth != null && auth.isAuthenticated() ) 
//        {
//        	String username;
//
//            Object principal = auth.getPrincipal();
//            if (principal instanceof UserDetails) {
//                username = ((UserDetails) principal).getUsername();
//            } else {
//                username = principal.toString();
//            }
//            
//            for(String st:selectedIds)
//            {
//            	
//            	HiredRelationS.save(new HiredRelation(username,st));
//            }
//            
//            
//        }
//    	return "sortingAccording_ATS_Score/ShowSelectedStudent";
//    }
//
//    
//    
//    
//  
//	  private double compareSkills(RequiredSkill studentSkills, RequiredSkill jobSkills) 
//	  {
//	  	Map<String, Double> studenttechnicalSkills=studentSkills.getTechnicalSkills();
//	  	List<String> studenttechnicalSkillsList=new ArrayList();
//	  	for (Map.Entry<String, Double> entry : studenttechnicalSkills.entrySet())
//	  	{
//	  	    String skill = entry.getKey();
//	  	    studenttechnicalSkillsList.add(skill);
//	  	}
//	  	
//	  	Map<String, Double> requiredtechnicalSkills=jobSkills.getTechnicalSkills();
//	  	
//	  	double ats_technicalSkills=comparetechnicalSkills(studenttechnicalSkillsList,requiredtechnicalSkills);
//	  	
//	  	
//	  	
//	  	Map<String, Double> studentDevelopmentSkills = studentSkills.getDevelopmentSkills();
//	  	List<String> studentDevelopmentSkillsList = new ArrayList<>();
//	  	for (Map.Entry<String, Double> entry : studentDevelopmentSkills.entrySet())
//	  	{
//	  	    String skill = entry.getKey();
//	  	    studentDevelopmentSkillsList.add(skill);
//	  	}
//	  	Map<String, Double> requiredDevelopmentSkills = jobSkills.getDevelopmentSkills();
//	  	double ats_developmentSkills = compareDevelopmentSkills(studentDevelopmentSkillsList, requiredDevelopmentSkills);
//	
//	  	
//	  	
//	  	Map<String, Double> studentFrameworkSkills = studentSkills.getFrameworkSkills();
//	  	List<String> studentFrameworkSkillsList = new ArrayList<>();
//	  	for (Map.Entry<String, Double> entry : studentFrameworkSkills.entrySet())
//	  	{
//	  	    String skill = entry.getKey();
//	  	    studentFrameworkSkillsList.add(skill);
//	  	}
//	  	Map<String, Double> requiredFrameworkSkills = jobSkills.getFrameworkSkills();
//	  	double ats_frameworkSkills = compareFrameworkSkills(studentFrameworkSkillsList, requiredFrameworkSkills);
//	  	
//	  	return ats_technicalSkills+ats_developmentSkills+ats_frameworkSkills;
//	  }
//	  
//	  private double comparetechnicalSkills(List<String> studenttechnicalSkills,Map<String, Double> requiredtechnicalSkills)
//	  {
//	  	double total=0;
//	  	for(String skill:studenttechnicalSkills)
//	  	{
//	  		total+=requiredtechnicalSkills.get(skill);
//	  	}
//	  	
//	  	return total;
//	  }
//	  
//	  private double compareDevelopmentSkills(List<String> studentDevelopmentSkills, Map<String, Double> requiredDevelopmentSkills)
//	  {
//	      double total = 0;
//	      for (String skill : studentDevelopmentSkills)
//	      {
//	          total += requiredDevelopmentSkills.get(skill);
//	      }
//	      return total;
//	  }
//	
//	  private double compareFrameworkSkills(List<String> studentFrameworkSkills, Map<String, Double> requiredFrameworkSkills)
//	  {
//	      double total = 0;
//	      for (String skill : studentFrameworkSkills)
//	      {
//	          total += requiredFrameworkSkills.get(skill);
//	      }
//	      return total;
//	  }
//}
