package com.kotiki.utils;

import com.kotiki.dto.UserDTO;
import entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapping {
    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setOwnerId(user.getOwner());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}
