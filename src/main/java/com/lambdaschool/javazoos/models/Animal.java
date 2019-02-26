package com.lambdaschool.javazoos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long animalid;

    private String animaltype;

    private String animalname;

    @ManyToMany
    @JoinTable(name = "zooanimals",
            joinColumns = {@JoinColumn(name = "animalid")},
            inverseJoinColumns = {@JoinColumn(name = "zooid")})
    @JsonIgnoreProperties("animals")
    private Set<Zoo> zoos  = new HashSet<>();

}
