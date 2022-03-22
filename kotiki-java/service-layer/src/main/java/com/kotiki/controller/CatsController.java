package com.kotiki.controller;

import com.kotiki.dto.CatDTO;
import com.kotiki.exceptions.NotFoundByIdException;
import com.kotiki.service.CatsService;
import com.kotiki.utils.CatDtoMapping;
import entities.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    public ResponseEntity getAllCats() {
        List<Cat> cats = catsService.getAllCats();
        List<CatDTO> catsDTO = cats
                .stream()
                .map(i -> catDtoMapping.mapToDTO(i))
                .collect(Collectors.toList());
        return new ResponseEntity(catsDTO, HttpStatus.OK);
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
    public ResponseEntity getCat(@PathVariable Long id) {
        Cat cat = null;
        CatDTO catDTO = null;
        try {
            cat = catsService.findCatById(id);
            catDTO = catDtoMapping.mapToDTO(cat);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(catDTO, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity deleteCat(@PathVariable Long id) {
        catsService.deleteCatById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/all/delete")
    public ResponseEntity deleteAllCats() {
        catsService.deleteAllCats();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{id}/friends/add/{friendId}")
    public ResponseEntity addFriendToCat(
            @PathVariable Long id,
            @PathVariable Long friendId
    ) {
        try {
            catsService.addFriend(id, friendId);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
