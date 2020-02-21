package org.carpetati.spring.backend.entity.dao;

import org.carpetati.spring.backend.entity.TipoProblema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProblemaRepository extends JpaRepository<TipoProblema, Long>{

}
