package controller;

import breeds.Breed;
import cats.Cat;
import com.sun.istack.NotNull;
import exceptions.NotFoundByIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owner.Owner;
import service.KotikiService;

import java.util.GregorianCalendar;
import java.util.List;

@RestController
@RequestMapping("/kotiki/")
public class Controller {
    private final KotikiService kotikiService;

    @Autowired
    public Controller(KotikiService kotikiService) {
        this.kotikiService = kotikiService;
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity getFriendsOfCat(@RequestParam Long id) {
        List<Cat> friends = null;
        try {
            friends = kotikiService.findFriendsOfCat(id);
        } catch (NotFoundByIdException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(friends, HttpStatus.OK);
    }

    @GetMapping("/cats")
    public ResponseEntity getAllCats() {
        List<Cat> cats = kotikiService.findAllCats();
        return new ResponseEntity(cats, HttpStatus.OK);
    }

    @GetMapping("/owners")
    public ResponseEntity getAllOwners() {
        List<Owner> owners = kotikiService.findAllOwners();
        return new ResponseEntity(owners, HttpStatus.OK);
    }

    @PostMapping("/createcat")
    public ResponseEntity createCat(
            @RequestParam("name") @NotNull String name,
            @RequestParam("dateOfBirth") @NotNull GregorianCalendar dateOfBirth,
            @RequestParam("owner") Long ownerId,
            @RequestParam("breed") @NotNull Breed breed
    ) {
        Long id = kotikiService.createNewCat(name, dateOfBirth, breed, ownerId);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCat(@PathVariable Long id) {
        Cat cat = kotikiService.findCat();

    }

    @GetMapping("/cats/allwithcriteria")
    public ResponseEntity getAllCatsWithCriteria(
            @RequestParam("name") String name,
            @RequestParam("breed") Breed breed,
            @RequestParam("dateOfBirth") GregorianCalendar dateOfBirth
    ) {
        List<Cat> cats = kotikiService.findAllCatsByCriteria(String name, Breed breed, GregorianCalendar dateOfBirth);
        return new ResponseEntity(cats, HttpStatus.OK);
    }
}
