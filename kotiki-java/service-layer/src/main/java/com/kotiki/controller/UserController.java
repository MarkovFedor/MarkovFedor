package com.kotiki.controller;

import com.kotiki.service.UserService;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/kotiki/user")
public class UserController {
    @Autowired
    private UserService service;
    public UserController() {}

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN")
    public List<User>
}
