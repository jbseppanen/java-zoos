package com.lambdaschool.javazoos.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.lambdaschool.javazoos.models.Animal;
import com.lambdaschool.javazoos.models.Telephone;
import com.lambdaschool.javazoos.models.Zoo;
import com.lambdaschool.javazoos.repositories.AnimalRepository;
import com.lambdaschool.javazoos.repositories.TelephoneRepository;
import com.lambdaschool.javazoos.repositories.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    @Autowired
    ZooRepository zooRepo;

    @Autowired
    TelephoneRepository telephoneRepo;

    @Autowired
    AnimalRepository animalRepo;

    @PutMapping(value = "/zoos/{id}")
    public Zoo updateZooById(@PathVariable long id, @RequestBody Zoo zoo) throws URISyntaxException {
        Optional<Zoo> foundZoo = zooRepo.findById(id);
        if (foundZoo.isPresent()) {
            zoo.setZooid(id);
            zooRepo.save(zoo);
            return zoo;
        } else {
            return null;
        }
    }

    @PutMapping(value = "/telephones/{id}")
    public Telephone updateTelephoneById(@PathVariable long id, @RequestBody Telephone telephone) throws URISyntaxException {
        Optional<Telephone> foundTelephone = telephoneRepo.findById(id);
        if (foundTelephone.isPresent()) {
            telephone.setPhoneid(id);
            telephoneRepo.save(telephone);
            return telephone;
        } else {
            return null;
        }
    }

    @PutMapping(value = "/animals/{id}")
    public Animal updateAnimalById(@PathVariable long id, @RequestBody Animal animal) throws URISyntaxException {
        Optional<Animal> foundAnimal = animalRepo.findById(id);
        if (foundAnimal.isPresent()) {
            animal.setAnimalid(id);
            animalRepo.save(animal);
            return animal;
        } else {
            return null;
        }
    }

    @PostMapping(value = "/zoos")
    public Zoo addZoo(@RequestBody Zoo zoo) throws URISyntaxException {
        return zooRepo.save(zoo);
    }

    @PostMapping(value = "/phones")
    public Telephone addTelephone(@RequestBody Telephone telephone) throws URISyntaxException {
        return telephoneRepo.save(telephone);
    }

    @PostMapping(value = "/animals")
    public Animal addAnimal(@RequestBody Animal animal) throws URISyntaxException {
        return animalRepo.save(animal);
    }

    @PostMapping(value = "/zoos/animals")
    public ObjectNode addAnimalToZoo(@RequestBody ObjectNode zooanimal) {
        long zooid = zooanimal.get("zooid").asLong();
        long animalid = zooanimal.get("animalid").asLong();

        Optional<Zoo> foundZoo = zooRepo.findById(zooid);
        Optional<Animal> foundAnimal = animalRepo.findById(animalid);

        if (foundZoo.isPresent() && foundAnimal.isPresent()) {
           Animal animal = foundAnimal.get();
            animal.getZoos().add(foundZoo.get());
            animalRepo.save(animal);
            return zooanimal;
        } else {
            return null;
        }
    }
}
