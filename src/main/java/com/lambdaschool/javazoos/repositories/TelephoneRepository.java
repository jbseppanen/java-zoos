package com.lambdaschool.javazoos.repositories;

import com.lambdaschool.javazoos.models.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelephoneRepository extends JpaRepository<Telephone, Long> {
}
