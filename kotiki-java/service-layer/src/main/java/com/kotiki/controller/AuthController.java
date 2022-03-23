package com.kotiki.controller;

import com.kotiki.dto.UserDTO;
import com.kotiki.exceptions.UserWithUsernameExistsException;
import com.kotiki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity registerNewUser(@RequestBody UserDTO userDTO) {
        Long id = null;
        try {
            id = userService.register(userDTO);
        } catch (UserWithUsernameExistsException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(id, HttpStatus.CREATED);
    }
}
