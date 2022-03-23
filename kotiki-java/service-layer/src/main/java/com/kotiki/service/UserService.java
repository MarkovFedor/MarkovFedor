package com.kotiki.service;

import com.kotiki.dto.UserDTO;
import com.kotiki.exceptions.UserWithUsernameExistsException;
import com.kotiki.utils.UserDtoMapping;
import dao.IRoleDao;
import dao.IUserDao;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDtoMapping userDtoMapping;

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

    public Long register(UserDTO userDTO) throws UserWithUsernameExistsException {
        System.out.println("Service registration");
        User user = userDtoMapping.mapToUser(userDTO);
        System.out.println("After mapping");
        boolean userExists = userDao.existsByUsername(user.getUsername());
        System.out.println("After searching");
        if(userExists) {
            System.out.println("In if");
            throw new UserWithUsernameExistsException("This user exists");
        } else {
            System.out.println("In else");
            String userPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(userPassword));
            System.out.println("After encoding");
        }
        userDao.save(user);
        return user.getId();
    }
}
