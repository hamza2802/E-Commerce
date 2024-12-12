package com.techlabs.capstone.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.techlabs.capstone.service.RoleServiceImpl;
import com.techlabs.capstone.service.UserServiceImpl;

@Component
public class BeanInitializationOrder implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        initOrder();
    }

    private void initOrder() {
        RoleServiceImpl roleService = applicationContext.getBean(RoleServiceImpl.class);
        roleService.createRoles();  
        
        UserServiceImpl userService = applicationContext.getBean(UserServiceImpl.class);
        userService.initAdminUser(); 
    }
}

