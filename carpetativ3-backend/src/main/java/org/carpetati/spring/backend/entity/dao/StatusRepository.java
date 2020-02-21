package org.carpetati.spring.backend.entity.dao;

import org.carpetati.spring.backend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{

}
