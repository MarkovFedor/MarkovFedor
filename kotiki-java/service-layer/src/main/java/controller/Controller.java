package controller;

import breeds.Breed;
import cats.Cat;
import com.sun.istack.NotNull;
import dto.CatDTO;
import dto.OwnerDTO;
import exceptions.IncorrectCatParamsException;
import exceptions.NotFoundByIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owner.Owner;
import service.KotikiService;
import utils.CatDtoMapping;
import utils.OwnerDtoMapping;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kotiki/")
public class Controller {

    @Autowired
    private KotikiService kotikiService;

    @Autowired
    private OwnerDtoMapping ownerDtoMapping;

    @Autowired
    private CatDtoMapping catDtoMapping;

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

    @PostMapping("/createcat")
    public ResponseEntity createCat(@RequestBody CatDTO catDTO) {
        Long id = null;
        try {
            Cat cat = catDtoMapping.mapToEntity(catDTO);
            id = kotikiService.saveNewEntity(cat);
        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @PostMapping("/createowner")
    public ResponseEntity createOwner(@RequestBody OwnerDTO ownerDTO) {
        Long id = null;
        try {
            Owner owner = ownerDtoMapping.mapToEntity(ownerDTO);
            id = kotikiService.saveNewEntity(owner);
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
}
