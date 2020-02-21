package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Puesto;
import org.carpetati.spring.backend.entity.dao.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PuestoService implements IGenericService<Puesto> {
	@Autowired
	private PuestoRepository puestoDao;

	@Override
	public List<Puesto> findAll() {
		return puestoDao.findAll();
	}

	@Override
	public Puesto findById(Long id) {
		return puestoDao.findById(id).orElse(null);
	}

	@Override
	public Puesto save(Puesto entidad) {
		return puestoDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		puestoDao.deleteById(id);
	}

	@Override
	public Page<Puesto> findAll(Pageable p) {
		return puestoDao.findAll(p);
	}
}
