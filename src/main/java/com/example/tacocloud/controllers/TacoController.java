package com.example.tacocloud.controllers;

import com.example.tacocloud.repositories.TacoRepository;
import com.example.tacoclouddomain.entities.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {

    private final TacoRepository tacoRepository;

    public TacoController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> tacoById(@PathVariable long id) {
        Optional<Taco> taco = tacoRepository.findById(id);
        if (taco.isPresent()) {
            return ResponseEntity.ok(taco.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco saveTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

}
