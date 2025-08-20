package com.example.student_placementMVC.Security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig 
{
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) 
    {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        manager.setUsersByUsernameQuery
        (
            "SELECT user_id AS username, password, true AS enabled FROM users WHERE user_id = ?"
        );

        manager.setAuthoritiesByUsernameQuery(
        	    "SELECT u.user_id AS username, r.role_name AS authority " +
        	    "FROM users u " +
        	    "JOIN roles r ON u.role_name = r.role_name " +
        	    "WHERE u.user_id = ?"
        	);


        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
    {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/error").permitAll()
  
                .requestMatchers(
                		"/jobs/student-form",
                        "/jobs/addRequiredSkill",
                        "/jobs/save-skills",
                        "/jobs/company-form",
                        "/jobs/AddCompanyRequiredSkill",
                        "/jobs/save-company-skills",
                        "/jobs/Register",
                        "/jobs/admin-form",
                        "/jobs/register-conformed"
                    ).permitAll()

                    // COMPANY only
                    .requestMatchers(
                        "/jobs/submit",
                        "/jobs/Generate_ATS_Score"
                    ).hasAuthority("COMPANY")
                    
                    // STUDENT only
                    .requestMatchers(
                    	"/jobs/offered_company",
                    	"/jobs/applyForCompany/**"
                    ).hasAuthority("STUDENT")

                    // STUDENT or COMPANY can access
                    .requestMatchers(
                    	"/jobs/showAllCompany"
                    ).hasAnyAuthority("STUDENT", "COMPANY")
                    
                .anyRequest().authenticated()
            )
            
            


    
            .formLogin(form -> form
                .loginPage("/jobs/showMyLoginPage") 
                .loginProcessingUrl("/jobs/authenticateTheUser")
                .defaultSuccessUrl("/jobs/", true)
                .permitAll()
            )
            
            .logout(logout -> logout
                .logoutSuccessUrl("/jobs/showMyLoginPage?logout")                
                .permitAll()
            )
            
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/jobs/access-denied")
            );

        return http.build();
    }


}
