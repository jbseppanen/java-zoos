package com.lambdaschool.javazoos.repositories;

import com.lambdaschool.javazoos.models.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ZooRepository extends JpaRepository<Zoo, Long> {
    Zoo findByZoonameEqualsIgnoreCase(String name);

    @Transactional //Need both of these annotations to modify data.
    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE zooid = :zooid", nativeQuery = true)
    void deleteAnimalsFromzooanimalsByZooId(long zooid);

    @Transactional //Need both of these annotations to modify data.
    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE animalid = :animalid", nativeQuery = true)
    void deleteAnimalByIdFromzooanimals(long animalid);

}
