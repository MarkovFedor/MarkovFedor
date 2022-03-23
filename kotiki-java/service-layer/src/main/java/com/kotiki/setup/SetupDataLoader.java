package com.kotiki.setup;

import dao.IRoleDao;
import entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private IRoleDao roleDao;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createRoleIfNotFound("ADMIN", 1L);
        createRoleIfNotFound("USER", 2L);
    }

    private void createRoleIfNotFound(String name, Long id) {
        Role role = roleDao.findByName(name);
        if(role == null) {
            role = new Role();
            role.setId(id);
            role.setName(name);
            roleDao.save(role);
        }
    }
}
