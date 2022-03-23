package com.kotiki.utils;

import com.kotiki.dto.UserDTO;
import dao.IOwnerDao;
import dao.IRoleDao;
import entities.Owner;
import entities.Role;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDtoMapping {
    @Autowired
    private IOwnerDao ownerDao;

    @Autowired
    private IRoleDao roleDao;

    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setOwnerId(user.getOwner());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    public User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        Optional<Owner> owner = null;
        if(userDTO.getOwnerId()!=null) {
            owner = ownerDao.findById(userDTO.getOwnerId());
        }
        user.setOwner(userDTO.getOwnerId());
        Set<Role> roles = new HashSet<>();
        for(String role: userDTO.getRoles()) {
            roles.add(roleDao.findByName(role));
        }
        user.setRoles(roles);
        return user;
    }
}
