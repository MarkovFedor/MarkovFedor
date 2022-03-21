package com.kotiki.controller;

import entities.Cat;
import com.kotiki.dto.CatDTO;
import com.kotiki.dto.OwnerDTO;
import com.kotiki.exceptions.NotFoundByIdException;
import com.kotiki.service.KotikiService;
import com.kotiki.utils.CatDtoMapping;
import com.kotiki.utils.OwnerDtoMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import entities.Owner;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ComponentScan("com.kotiki.utils")
public class KotikiController {

    @Autowired
    private KotikiService kotikiService;

    @Autowired
    private OwnerDtoMapping ownerDtoMapping;

    @Autowired
    private CatDtoMapping catDtoMapping;

    public KotikiController() {

    }

    @GetMapping("/")
    public ResponseEntity testEmptyPath() {
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/cats")
    public ResponseEntity getAllCats() {
        List<Cat> cats = kotikiService.getAllCats();
        List<CatDTO> catsDTO = cats
                .stream()
                .map(i -> catDtoMapping.mapToDTO(i))
                .collect(Collectors.toList());
        return new ResponseEntity(catsDTO, HttpStatus.OK);
    }

    @GetMapping("/owners")
    public ResponseEntity getAllOwners() {
        List<Owner> owners = kotikiService.getAllOwners();
        List<OwnerDTO> ownersDTO = owners
                .stream()
                .map(i -> ownerDtoMapping.mapToDTO(i))
                .collect(Collectors.toList());
        return new ResponseEntity(owners, HttpStatus.OK);
    }

    @PostMapping("/add/cat")
    public ResponseEntity createCat(@RequestBody CatDTO catDTO) {
        Long id = null;
        try {
            Cat cat = catDtoMapping.mapToEntity(catDTO);
            id = kotikiService.saveNewCat(cat);
        } catch (ParseException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @PostMapping("/add/owner")
    public ResponseEntity createOwner(@RequestBody OwnerDTO ownerDTO) {
        Long id = null;
        try {
            Owner owner = ownerDtoMapping.mapToEntity(ownerDTO);
            id = kotikiService.saveNewOwner(owner);
        }catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCat(@PathVariable Long id) {
        Cat cat = null;
        CatDTO catDTO = null;
        try {
            cat = kotikiService.findCatById(id);
            catDTO = catDtoMapping.mapToDTO(cat);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(catDTO, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/delete/owner/{id}")
    public ResponseEntity deleteOwner(@PathVariable Long id) {
        kotikiService.deleteOwnerById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/delete/cat/{id}")
    public ResponseEntity deleteCat(@PathVariable Long id) {
        kotikiService.deleteCatById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/delete/cat/all")
    public ResponseEntity deleteAllCats() {
        kotikiService.deleteAllCats();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/delete/owner/all")
    public ResponseEntity deleteAllOwners() {
        kotikiService.deleteAllOwners();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/cat/{id}/addfriend/{friendId}")
    public ResponseEntity addFriendToCat(
            @PathVariable Long id,
            @PathVariable Long friendId
    ) {
        try {
            kotikiService.addFriend(id, friendId);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/owner/{id}/addcat/{catId}")
    public ResponseEntity addCatToOwner(
            @PathVariable Long id,
            @PathVariable Long catId
    ) {
        try {
            kotikiService.addCat(id, catId);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
