package controller;

import cats.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.KotikiService;

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
        List<Cat> friends = kotikiService.findFriendsOfCat(id);
        return new ResponseEntity(friends, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllCats() {
        List<Cat> cats = kotikiService.findAllCats();
        return new ResponseEntity(cats, HttpStatus.OK);
    }

    @PostMapping("/createcat")
    public ResponseEntity createCat(
            @RequestBody String name
    )
}
