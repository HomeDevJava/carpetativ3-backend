package org.carpetati.spring.backend.entity.dao;

import org.carpetati.spring.backend.entity.TipoSiniestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSiniestroRepository extends JpaRepository<TipoSiniestro, Long>{

}
