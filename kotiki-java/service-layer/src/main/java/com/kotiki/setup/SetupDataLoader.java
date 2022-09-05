package com.kotiki.setup;

import com.kotiki.dto.UserDTO;
import com.kotiki.exceptions.UserWithUsernameExistsException;
import com.kotiki.service.UserService;
import dao.IRoleDao;
import dao.IUserDao;
import entities.Role;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private UserService userService;

    @Autowired
    private IUserDao userDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createRoleIfNotFound("ADMIN", 1L);
        createRoleIfNotFound("USER", 2L);
        createAdminUserIfNotFound();
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

    private void createAdminUserIfNotFound() {
        User user = userDao.findByUsername("admin");
        if(user!=null) {
            return;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin");
        Role role = roleDao.findByName("ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        userDTO.setRoles(roles);
        userDTO.setPassword("admin");
        try {
            userService.register(userDTO);
        } catch (UserWithUsernameExistsException e) {
            e.printStackTrace();
        }
    }
}
