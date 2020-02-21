package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Status;
import org.carpetati.spring.backend.entity.dao.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StatusService implements IGenericService<Status> {
	@Autowired private StatusRepository statusDao;

	@Override
	public List<Status> findAll() {
		return statusDao.findAll();
	}

	@Override
	public Status findById(Long id) {
		return statusDao.findById(id).orElse(null);
	}

	@Override
	public Status save(Status entidad) {
		return statusDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		statusDao.deleteById(id);		
	}

	@Override
	public Page<Status> findAll(Pageable p) {
		return statusDao.findAll(p);
	}

}
