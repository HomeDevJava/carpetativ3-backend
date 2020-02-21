package org.carpetati.spring.backend.entity.dao;

import org.carpetati.spring.backend.entity.Cedi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CediRepository extends JpaRepository<Cedi, Long> {

}
