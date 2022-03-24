package com.kotiki.controller;

import com.kotiki.dto.CatDTO;
import com.kotiki.exceptions.AccessToStrangersEntityException;
import com.kotiki.exceptions.NotFoundByIdException;
import com.kotiki.service.CatsService;
import com.kotiki.service.UserService;
import com.kotiki.utils.CatDtoMapping;
import entities.Cat;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ComponentScan("com.kotiki.utils")
@RequestMapping("/kotiki/cats")
public class CatsController {
    @Autowired
    private CatsService catsService;

    @Autowired
    private CatDtoMapping catDtoMapping;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity getAllCats(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        try {
            List<Cat> cats = userService.getAllCatsOfUser(user);
            List<CatDTO> catsDTO = cats.stream()
                    .map(i -> catDtoMapping.mapToDTO(i))
                    .collect(Collectors.toList());
            return new ResponseEntity(catsDTO, HttpStatus.OK);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity createCat(@RequestBody CatDTO catDTO) {
        Long id = null;
        try {
            Cat cat = catDtoMapping.mapToEntity(catDTO);
            id = catsService.saveNewCat(cat);
        } catch (ParseException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCat(@PathVariable Long id, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        try {
            Cat cat = userService.getCatById(user,id);
            CatDTO catDTO = catDtoMapping.mapToDTO(cat);
            return new ResponseEntity(catDTO, HttpStatus.OK);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (AccessToStrangersEntityException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity deleteCat(Principal principal,@PathVariable Long id) {
        User user = userService.loadUserByUsername(principal.getName());
        try {
            userService.deleteCatOfUser(user, id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AccessToStrangersEntityException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    //ПЕРЕНЕСЕН В АДМИНКУ ТАК КАК ЗАТРАГИВАЕТ ВСЕХ КОТИКОВ
//    @PostMapping("/all/delete")
//    public ResponseEntity deleteAllCats() {
//        catsService.deleteAllCats();
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @PostMapping("/{id}/friends/add/{friendId}")
    public ResponseEntity addFriendToCat(
            Principal principal,
            @PathVariable Long id,
            @PathVariable Long friendId
    ) {
        User user = userService.loadUserByUsername(principal.getName());
        try {
            userService.addFriendToCat(user, id, friendId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (AccessToStrangersEntityException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
