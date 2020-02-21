package org.carpetati.spring.backend.entity.dao;

import org.carpetati.spring.backend.entity.Reparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReparacionRepository extends JpaRepository<Reparacion, Long> {

}
