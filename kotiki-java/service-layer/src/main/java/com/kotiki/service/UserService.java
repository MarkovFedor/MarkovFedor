package com.kotiki.service;

import com.kotiki.dto.UserDTO;
import com.kotiki.exceptions.NotFoundByIdException;
import com.kotiki.exceptions.UserWithUsernameExistsException;
import com.kotiki.utils.UserDtoMapping;
import dao.IRoleDao;
import dao.IUserDao;
import entities.Role;
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
        User user = userDtoMapping.mapToUser(userDTO);
        boolean userExists = userDao.existsByUsername(user.getUsername());
        if(userExists) {
            throw new UserWithUsernameExistsException("This user exists");
        } else {
            String userPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(userPassword));
            user.addRole(roleDao.findById(2L).get());
        }
        userDao.save(user);
        return user.getId();
    }

    public void giveRole(Long id, Long roleId) throws NotFoundByIdException {
        Optional<Role> role = roleDao.findById(roleId);
        Optional<User> user = userDao.findById(id);
        if(role.isPresent() & user.isPresent()) {
            user.get().addRole(role.get());
        } else {
            throw new NotFoundByIdException("Not found");
        }
        userDao.save(user.get());
    }
}
