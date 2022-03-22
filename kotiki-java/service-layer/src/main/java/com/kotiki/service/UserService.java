package com.kotiki.service;

import dao.IRoleDao;
import dao.IUserDao;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User with this name not found");
        }

        return user;
    }

    public User findUserById(Long id) throws ChangeSetPersister.NotFoundException {
        Optional<User> userFromDatabase = userDao.findById(id);
        return userFromDatabase.orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public List<User> allUsers() {
        return userDao.findAll();
    }
}
