package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Empleado;
import org.carpetati.spring.backend.entity.dao.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = "empleadoService")
public class EmpleadoService implements IGenericService<Empleado> {

	@Autowired
	private EmpleadoRepository empleadoDao;

	@Override
	public List<Empleado> findAll() {
		return empleadoDao.findAll();
	}

	@Override
	public Empleado findById(Long id) {
		return empleadoDao.findById(id).orElse(null);
	}

	@Override
	public Empleado save(Empleado entidad) {
		return empleadoDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		empleadoDao.deleteById(id);
	}

	@Override
	public Page<Empleado> findAll(Pageable p) {
		return empleadoDao.findAll(p);
	}
}
