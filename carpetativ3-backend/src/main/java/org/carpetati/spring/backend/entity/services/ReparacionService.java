package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Reparacion;
import org.carpetati.spring.backend.entity.dao.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReparacionService implements IGenericService<Reparacion> {
	
	@Autowired ReparacionRepository reparacionDao;

	@Override
	public List<Reparacion> findAll() {		
		return reparacionDao.findAll();
	}

	@Override
	public Page<Reparacion> findAll(Pageable p) {		
		return reparacionDao.findAll(p);
	}

	@Override
	public Reparacion findById(Long id) {		
		return reparacionDao.findById(id).orElse(null);
	}

	@Override
	public Reparacion save(Reparacion entidad) {		
		return reparacionDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		reparacionDao.deleteById(id);		
	}

}
