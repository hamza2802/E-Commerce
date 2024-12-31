package com.techlabs.capstone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.techlabs.capstone.service.RoleServiceImpl;
import com.techlabs.capstone.service.AdminServiceImpl;

@Component
public class BeanInitializationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private AdminServiceImpl adminService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
       
        roleService.createRoles();
        
        adminService.initAdminUser();
    }
}
 