package com.kotiki.service;

import com.kotiki.dto.UserDTO;
import com.kotiki.exceptions.AccessToStrangersEntityException;
import com.kotiki.exceptions.NotCreatedOwnerException;
import com.kotiki.exceptions.NotFoundByIdException;
import com.kotiki.exceptions.UserWithUsernameExistsException;
import com.kotiki.utils.UserDtoMapping;
import dao.ICatsDao;
import dao.IOwnerDao;
import dao.IRoleDao;
import dao.IUserDao;
import entities.Cat;
import entities.Owner;
import entities.Role;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    @Autowired
    private IOwnerDao ownerDao;

    @Autowired
    private ICatsDao catsDao;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
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

    public void setOwnerToUser(User user, Long ownerId) {
        user.setOwner(ownerId);
        System.out.println(user);
        userDao.save(user);
    }

    public void deleteOwnerOfUser(User user) {
        user.setOwner(null);
        userDao.save(user);
        Long ownerId = user.getOwner();
        ownerDao.deleteById(ownerId);
    }

    public Owner getOwnerOfUser(User user) throws NotFoundByIdException, NotCreatedOwnerException {
        if(user.getOwner() == null) {
            throw new NotCreatedOwnerException("У данного пользователя нет владельца");
        }
        Optional<Owner> owner = ownerDao.findById(user.getOwner());
        if(owner.isEmpty()) {
            throw new NotFoundByIdException("Такого владельца нет");
        }
        return owner.get();
    }

    public List<Cat> getAllCatsOfUser(User user) throws NotFoundByIdException {
        Optional<Owner> owner = ownerDao.findById(user.getOwner());
        if(owner.isEmpty()) {
            throw new NotFoundByIdException("Owner пользователя не найден");
        }
        List<Cat> cats = owner.get().getCats();
        return cats;
    }

    public Cat getCatById(User user, Long id) throws NotFoundByIdException, AccessToStrangersEntityException {
        Optional<Owner> owner = null;
        if(user.getOwner() != null) {
            owner = ownerDao.findById(user.getOwner());
        }
        if(owner.isEmpty()) {
            throw new NotFoundByIdException("Not found");
        }
        Optional<Cat> cat = catsDao.findById(id);
        if(cat.isEmpty()) {
            throw new NotFoundByIdException("Not found");
        }
        if(cat.get().getOwner().getId() == owner.get().getId()) {
            return cat.get();
        } else {
            throw new AccessToStrangersEntityException("Not your cat");
        }
    }

    public void deleteCatOfUser(User user, Long id) throws AccessToStrangersEntityException, NotFoundByIdException {
        Optional<Owner> owner = null;
        if(user.getOwner() != null) {
            owner = ownerDao.findById(user.getOwner());
        }
        if(owner.isEmpty()) {
            throw new NotFoundByIdException("Not found");
        }
        Optional<Cat> cat = catsDao.findById(id);
        if(cat.isPresent()) {
            if(cat.get().getOwner().getId() == owner.get().getId()) {
                owner.get().getCats().remove(cat);
                ownerDao.save(owner.get());
                catsDao.save(cat.get());
            } else {
                throw new AccessToStrangersEntityException("Not your cat");
            }
        }
    }

    public void addFriendToCat(User user, Long id, Long friendId) throws NotFoundByIdException, AccessToStrangersEntityException {
        Optional<Cat> cat = null;
        Optional<Cat> friend = null;
        Optional<Owner> owner = null;
        if(user.getOwner()!=null) {
            owner = ownerDao.findById(user.getOwner());
        }
        if(owner.isEmpty()) {
            throw new NotFoundByIdException("Not found");
        }
        cat = catsDao.findById(id);
        friend = catsDao.findById(friendId);
        if(cat.isPresent() & friend.isPresent()) {
            if(cat.get().getOwner().getId() == owner.get().getId() & friend.get().getOwner().getId() == owner.get().getId()) {
                cat.get().addFriend(friend.get());
                catsDao.save(cat.get());
                catsDao.save(friend.get());
            } else {
                throw new AccessToStrangersEntityException("Not your cat");
            }
        }
    }
}
