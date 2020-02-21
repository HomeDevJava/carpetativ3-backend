package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Cedi;
import org.carpetati.spring.backend.entity.dao.CediRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = "cediService")
public class CediService implements IGenericService<Cedi> {

	@Autowired
	private CediRepository cediDao;

	@Override
	public List<Cedi> findAll() {
		return cediDao.findAll();
	}

	@Override
	public Cedi findById(Long id) {
		return cediDao.findById(id).orElse(null);
	}

	@Override
	public Cedi save(Cedi entidad) {
		return cediDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		cediDao.deleteById(id);
	}

	@Override
	public Page<Cedi> findAll(Pageable p) {
		return cediDao.findAll(p);
	}

}