package com.example.tacocloud.controllers;

import com.example.tacocloud.repositories.TacoRepository;
import com.example.tacoclouddomain.entities.Taco;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {

    private final TacoRepository tacoRepository;

    public TacoController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(params = "recent")
    public Flux<Taco> recentTacos() {
        return tacoRepository.findAll().take(12);
    }

    @GetMapping("{id}")
    public Mono<Taco> tacoById(@PathVariable long id) {
        return tacoRepository.findById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> saveTaco(@RequestBody Mono<Taco> taco) {
        return tacoRepository.saveAll(taco).next();
    }

}
