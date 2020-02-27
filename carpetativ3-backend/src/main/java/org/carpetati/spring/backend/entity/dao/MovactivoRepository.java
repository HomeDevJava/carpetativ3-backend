package org.carpetati.spring.backend.entity.dao;

import org.carpetati.spring.backend.entity.Movactivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovactivoRepository extends JpaRepository<Movactivo, Long> {

}
