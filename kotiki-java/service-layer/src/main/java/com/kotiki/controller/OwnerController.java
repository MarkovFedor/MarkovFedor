package com.kotiki.controller;

import com.kotiki.dto.CatDTO;
import com.kotiki.dto.OwnerDTO;
import com.kotiki.dto.UserDTO;
import com.kotiki.exceptions.NotCreatedOwnerException;
import com.kotiki.exceptions.NotFoundByIdException;
import com.kotiki.service.OwnerService;
import com.kotiki.service.UserService;
import com.kotiki.utils.OwnerDtoMapping;
import com.kotiki.utils.UserDtoMapping;
import entities.Cat;
import entities.Owner;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/kotiki/owner")
@ComponentScan("com.kotiki.utils")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerDtoMapping ownerDtoMapping;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoMapping userDtoMapping;

    @GetMapping("/all")
    public ResponseEntity getAllOwners() {
        List<Owner> owners = ownerService.getAllOwners();
        List<OwnerDTO> ownersDTO = owners
                .stream()
                .map(i -> ownerDtoMapping.mapToDTO(i))
                .collect(Collectors.toList());
        return new ResponseEntity(owners, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity getOwner(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        Owner owner = null;
        try {
            owner = userService.getOwnerOfUser(user);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (NotCreatedOwnerException e) {
            return new ResponseEntity("У текущего пользователя нет владельца", HttpStatus.BAD_REQUEST);
        }
        OwnerDTO ownerDTO = ownerDtoMapping.mapToDTO(owner);
        return new ResponseEntity(ownerDTO, HttpStatus.OK);
    }

    @GetMapping("/currentuser")
    public ResponseEntity getCurrentUser(Authentication principal) {
        User user = userService.loadUserByUsername(principal.getName());
        UserDTO userDTO = userDtoMapping.mapToUserDTO(user);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity createOwner(@RequestBody OwnerDTO ownerDTO, Principal principal) {
        System.out.println("Here");
        Long id = null;
        try {
            User user = userService.loadUserByUsername(principal.getName());
            Owner owner = ownerDtoMapping.mapToEntity(ownerDTO);
            if(user.getOwner() != null) {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
            id = ownerService.saveNewOwner(owner);
            userService.setOwnerToUser(user, owner.getId());
        }catch(Exception e) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteOwner(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        userService.deleteOwnerOfUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    //ПЕРЕНЕСЕН В АДМИНКУ, ТАК КАК ЗАТРАГИВАЕТ ВСЕХ OWNERов
//    @PostMapping("/all/delete")
//    public ResponseEntity deleteAllOwners() {
//        ownerService.deleteAllOwners();
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @PostMapping("/addcat/{catId}")
    public ResponseEntity addCatToOwner(
            Principal principal,
            @PathVariable Long catId
    ) {
        try {
            User user = userService.loadUserByUsername(principal.getName());
            ownerService.addCat(user.getOwner(),catId);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/deletecat/{catId}")
    public ResponseEntity deleteCatOfOwner(
            Principal principal,
            @PathVariable Long catId
    ) {
        User user = userService.loadUserByUsername(principal.getName());
        try {
            ownerService.deleteCatFromOwner(user.getOwner(), catId);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
