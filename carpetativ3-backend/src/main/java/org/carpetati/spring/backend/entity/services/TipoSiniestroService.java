package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.TipoSiniestro;
import org.carpetati.spring.backend.entity.dao.TipoSiniestroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TipoSiniestroService implements IGenericService<TipoSiniestro> {
	@Autowired private TipoSiniestroRepository tiposiniestroDao;

	@Override
	public List<TipoSiniestro> findAll() {
		return tiposiniestroDao.findAll();
	}

	@Override
	public TipoSiniestro findById(Long id) {
		return tiposiniestroDao.findById(id).orElse(null);
	}

	@Override
	public TipoSiniestro save(TipoSiniestro entidad) {
		return tiposiniestroDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		tiposiniestroDao.deleteById(id);		
	}

	@Override
	public Page<TipoSiniestro> findAll(Pageable p) {
		return tiposiniestroDao.findAll(p);
	}

}