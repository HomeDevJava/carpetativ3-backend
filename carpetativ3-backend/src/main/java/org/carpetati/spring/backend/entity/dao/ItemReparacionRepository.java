package org.carpetati.spring.backend.entity.dao;

import org.carpetati.spring.backend.entity.ItemReparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReparacionRepository extends JpaRepository<ItemReparacion, Long> {

}
