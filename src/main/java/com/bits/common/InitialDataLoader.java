package com.bits.common;

import java.util.Arrays;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bits.auth.Role;
import com.bits.auth.RoleName;
import com.bits.auth.RoleRepository;
import com.bits.auth.User;
import com.bits.auth.UserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private RoleRepository roleRepository;
  
    @Value("${auth.adminuser}")
    private  String adminUser;
    
    @Value("${auth.adminpassword}")
    private  String adminPassword;
  
    @Autowired
    private PasswordEncoder passwordEncoder;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        if (alreadySetup)
            return;       
        
        createRoleIfNotFound(RoleName.ROLE_ADMIN);
        createRoleIfNotFound(RoleName.ROLE_USER);
 
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
        
        User user = userRepository.findByUsername(adminUser);
        if(user == null){
	        user = new User();
	        user.setFirstname("admin");
	        user.setLastname("admin");
	        user.setPassword(passwordEncoder.encode(adminPassword));
	        user.setRoles(Arrays.asList(adminRole));
	        user.setEnabled(true);
	        user.setUsername("admin@admin.com");
	        user.setLastPasswordResetDate(Calendar.getInstance().getTime());
	        userRepository.save(user);
        }
 
        alreadySetup = true;
    }
 
 
    @Transactional
    private Role createRoleIfNotFound(RoleName name) {
  
    	Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setTitle(role.toString());
            roleRepository.save(role);
        }
        return role;
    }
    
}
