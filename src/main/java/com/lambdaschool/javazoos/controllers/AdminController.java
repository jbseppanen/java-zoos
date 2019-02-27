package com.lambdaschool.javazoos.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lambdaschool.javazoos.models.Animal;
import com.lambdaschool.javazoos.models.Telephone;
import com.lambdaschool.javazoos.models.Zoo;
import com.lambdaschool.javazoos.repositories.AnimalRepository;
import com.lambdaschool.javazoos.repositories.TelephoneRepository;
import com.lambdaschool.javazoos.repositories.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
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

    @DeleteMapping(value = "/zoos/{id}")
    public String deleteZooById(@PathVariable long id) {
        Optional<Zoo> foundZoo = zooRepo.findById(id);
        if (foundZoo.isPresent()) {
            for (Telephone phone : foundZoo.get().getTelephones()) {
                telephoneRepo.delete(phone);
            }
            zooRepo.deleteAnimalsFromzooanimalsByZooId(id);
            zooRepo.deleteById(id);
            return "{" + "\"Success\":" + "\"Zoo has been deleted.\"" + "}";
        } else {
            return "{" + "\"Error\":" + "\"Zoo not found to delete.\"" + "}";
        }

    }

    @DeleteMapping(value = "/phones/{id}")
    public String deleteTelephoneById(@PathVariable long id) {
        Optional<Telephone> foundTelephone = telephoneRepo.findById(id);
        if (foundTelephone.isPresent()) {
            telephoneRepo.delete(foundTelephone.get());
            return "{" + "\"Success\":" + "\"Telephone has been deleted.\"" + "}";
        } else {
            return "{" + "\"Error\":" + "\"Phone not found to delete.\"" + "}";
        }
    }

    @DeleteMapping(value = "/animals/{id}")
    public String deleteAnimalById(@PathVariable long id) {
        Optional<Animal> foundAnimal = animalRepo.findById(id);
        if (foundAnimal.isPresent()) {
            animalRepo.deleteById(id);
            return "{" + "\"Success\":" + "\"Animal has been deleted.\"" + "}";
        } else {
            return "{" + "\"Error\":" + "\"Animal not found to delete.\"" + "}";
        }
    }

    @DeleteMapping(value = "/zoos/{zooid}/animals/{animalid}")
    public String removeAnimalByIdFromZooById(@PathVariable long zooid, @PathVariable long animalid) {
        Optional<Zoo> foundZoo = zooRepo.findById(zooid);
        Optional<Animal> foundAnimal = animalRepo.findById(animalid);

        if (foundZoo.isPresent() && foundAnimal.isPresent()) {
            Animal animal = foundAnimal.get();
            animal.getZoos().remove(foundZoo.get());
            animalRepo.save(animal);
            return "{" + "\"Success\":" + "\"Animal has been removed from zoo.\"" + "}";
        } else if (!foundZoo.isPresent()){
            return "{" + "\"Error\":" + "\"Zoo not found to delete animal from.\"" + "}";
        } else if (!foundAnimal.isPresent()) {
            return "{" + "\"Error\":" + "\"Animal not found to remove from zoo.\"" + "}";
        } else {
            return "{" + "\"Error\":" + "\"Unknown error.\"" + "}";
        }

    }

    @GetMapping(value = "/phones")
    public List<Telephone> listAllPhones() {
        return telephoneRepo.findAll();
    }
}
