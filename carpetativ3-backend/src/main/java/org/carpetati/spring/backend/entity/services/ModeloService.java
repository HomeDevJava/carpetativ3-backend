package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Modelo;
import org.carpetati.spring.backend.entity.dao.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ModeloService implements IGenericService<Modelo> {
	@Autowired
	private ModeloRepository modeloDao;

	@Override
	public List<Modelo> findAll() {
		return modeloDao.findAll();
	}

	@Override
	public Modelo findById(Long id) {
		return modeloDao.findById(id).orElse(null);
	}

	@Override
	public Modelo save(Modelo entidad) {
		return modeloDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		modeloDao.deleteById(id);
	}

	@Override
	public Page<Modelo> findAll(Pageable p) {
		return modeloDao.findAll(p);
	}
}
