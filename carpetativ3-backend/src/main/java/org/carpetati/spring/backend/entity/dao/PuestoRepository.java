package org.carpetati.spring.backend.entity.dao;

import org.carpetati.spring.backend.entity.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> {

}
