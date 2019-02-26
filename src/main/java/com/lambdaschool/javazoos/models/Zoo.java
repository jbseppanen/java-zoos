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
@Table(name = "zoo")
public class Zoo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long zooid;

    private String zooname;

    @OneToMany(mappedBy = "zoo")
    @JsonIgnoreProperties("zoo")
    private Set<Telephone> telephones  = new HashSet<>();

    @ManyToMany(mappedBy = "zoo")
    @JsonIgnoreProperties("zoo")
    private Set<Animal> animals = new HashSet<>();
}
