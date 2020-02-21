package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.TipoProblema;
import org.carpetati.spring.backend.entity.dao.TipoProblemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TipoProblemaService implements IGenericService<TipoProblema> {
	@Autowired private TipoProblemaRepository tipoproblemaDao;

	@Override
	public List<TipoProblema> findAll() {
		return tipoproblemaDao.findAll();
	}

	@Override
	public TipoProblema findById(Long id) {
		return tipoproblemaDao.findById(id).orElse(null);
	}

	@Override
	public TipoProblema save(TipoProblema entidad) {
		return tipoproblemaDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		tipoproblemaDao.deleteById(id);		
	}

	@Override
	public Page<TipoProblema> findAll(Pageable p) {
		return tipoproblemaDao.findAll(p);
	}
}
