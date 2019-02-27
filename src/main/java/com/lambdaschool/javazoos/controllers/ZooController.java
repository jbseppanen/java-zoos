package com.lambdaschool.javazoos.controllers;

import com.lambdaschool.javazoos.models.Zoo;
import com.lambdaschool.javazoos.repositories.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/zoos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ZooController {

    @Autowired
    ZooRepository zooRepo;

    @GetMapping(value = "/zoos")
    public List<Zoo> listAllZoos() {
        return zooRepo.findAll();
    }

    @GetMapping(value = "/{name}")
    public Zoo getZooByName(@PathVariable String name) {
        return zooRepo.findByZoonameEqualsIgnoreCase(name);
    }

}
