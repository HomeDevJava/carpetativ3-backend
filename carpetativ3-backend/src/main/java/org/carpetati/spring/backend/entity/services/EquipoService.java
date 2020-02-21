package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Equipo;
import org.carpetati.spring.backend.entity.dao.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EquipoService implements IGenericService<Equipo> {

	@Autowired
	private EquipoRepository equipoDao;

	@Override
	public List<Equipo> findAll() {
		return equipoDao.findAll();
	}

	@Override
	public Equipo findById(Long id) {
		return equipoDao.findById(id).orElse(null);
	}

	@Override
	public Equipo save(Equipo entidad) {
		return equipoDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		equipoDao.deleteById(id);
	}

	@Override
	public Page<Equipo> findAll(Pageable p) {
		return equipoDao.findAll(p);
	}
}