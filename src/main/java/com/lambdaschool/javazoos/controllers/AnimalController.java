package com.lambdaschool.javazoos.controllers;

import com.lambdaschool.javazoos.models.Animal;
import com.lambdaschool.javazoos.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/animals", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController {

    @Autowired
    AnimalRepository animalRepo;

    @GetMapping(value = "/animals")
    public List<Animal> listAllZoos() {
        return animalRepo.findAll();
    }

    @GetMapping(value = "/{name}")
    public Animal getAnimalByName(@PathVariable String name) {
        return animalRepo.findByAnimaltypeEqualsIgnoreCase(name);
    }
}