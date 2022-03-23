package com.kotiki.dto;

import com.sun.istack.NotNull;
import entities.Role;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private Long id;
    private Long ownerId;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private Set<String> roles;

    public UserDTO() {
        roles = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoles(Set<Role> rolesOfEntity) {
        for(Role role: rolesOfEntity) {
            roles.add(role.getName());
        }
    }
}
