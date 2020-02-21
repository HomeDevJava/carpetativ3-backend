package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Marca;
import org.carpetati.spring.backend.entity.dao.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MarcaService implements IGenericService<Marca> {

	@Autowired private MarcaRepository marcaDao;

	@Override
	public List<Marca> findAll() {
		return marcaDao.findAll();
	}

	@Override
	public Marca findById(Long id) {
		return marcaDao.findById(id).orElse(null);
	}

	@Override
	public Marca save(Marca entidad) {
		return marcaDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		marcaDao.deleteById(id);		
	}

	@Override
	public Page<Marca> findAll(Pageable p) {
		return marcaDao.findAll(p);
	}
}
