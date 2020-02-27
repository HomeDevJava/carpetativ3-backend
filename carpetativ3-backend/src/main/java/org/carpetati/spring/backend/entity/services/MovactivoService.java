package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Movactivo;
import org.carpetati.spring.backend.entity.dao.MovactivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovactivoService implements IGenericService<Movactivo> {
	
	@Autowired private MovactivoRepository movactivoDao;

	@Override
	public List<Movactivo> findAll() {		
		return movactivoDao.findAll();
	}

	@Override
	public Page<Movactivo> findAll(Pageable p) {		
		return movactivoDao.findAll(p);
	}

	@Override
	public Movactivo findById(Long id) {		
		return movactivoDao.findById(id).orElse(null);
	}

	@Override
	public Movactivo save(Movactivo entidad) {		
		return movactivoDao.save(entidad);
	}

	@Override
	public void delete(Long id) {		
		movactivoDao.deleteById(id);
	}

}
