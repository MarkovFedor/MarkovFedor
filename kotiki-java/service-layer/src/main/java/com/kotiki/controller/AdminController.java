package com.kotiki.controller;

import com.kotiki.dto.UserDTO;
import com.kotiki.exceptions.NotFoundByIdException;
import com.kotiki.service.UserService;
import com.kotiki.utils.UserDtoMapping;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/kotiki/admin")
public class AdminController {
    @Autowired
    private UserService service;

    @Autowired
    private UserDtoMapping userDTOMapping;
    public AdminController() {}

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getAllUsers() {
        List<UserDTO> users = service.allUsers()
                .stream()
                .map(i -> userDTOMapping.mapToUserDTO(i))
                .collect(Collectors.toList());
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        User user = null;
        UserDTO userDTO = null;
        try {
            user = service.findUserById(id);
            userDTO = userDTOMapping.mapToUserDTO(user);
            return new ResponseEntity(userDTO, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getid")
    public ResponseEntity getName(Principal principal) {
        String username = principal.getName();
        return new ResponseEntity(username, HttpStatus.OK);
    }

    @PostMapping("/giverole/{id}/{roleid}")
    public ResponseEntity giveRole(@PathVariable Long id, @PathVariable Long roleId) {
        try {
            service.giveRole(id, roleId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
