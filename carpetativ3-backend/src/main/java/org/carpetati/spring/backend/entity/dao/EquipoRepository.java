package org.carpetati.spring.backend.entity.dao;

import org.carpetati.spring.backend.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

}
