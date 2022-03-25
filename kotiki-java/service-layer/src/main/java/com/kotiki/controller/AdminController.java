package com.kotiki.controller;

import com.kotiki.dto.UserDTO;
import com.kotiki.exceptions.NotFoundByIdException;
import com.kotiki.service.CatsService;
import com.kotiki.service.OwnerService;
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

    @Autowired
    private CatsService catsService;

    @Autowired
    private OwnerService ownerService;

    public AdminController() {}

    @GetMapping("/all")
    public ResponseEntity getAllUsers() {
        List<UserDTO> users = service.allUsers()
                .stream()
                .map(i -> userDTOMapping.mapToUserDTO(i))
                .collect(Collectors.toList());
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
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

    @PostMapping("/giverole/{id}/{roleid}")
    public ResponseEntity giveRole(@PathVariable Long id, @PathVariable Long roleId) {
        try {
            service.giveRole(id, roleId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cats/all/delete")
    public ResponseEntity deleteAllCats() {
        catsService.deleteAllCats();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/owners/all/delete")
    public ResponseEntity deleteAllOwners() {
        ownerService.deleteAllOwners();
        return new ResponseEntity(HttpStatus.OK);
    }
}
