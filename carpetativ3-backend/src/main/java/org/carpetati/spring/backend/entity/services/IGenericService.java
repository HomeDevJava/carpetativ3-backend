package org.carpetati.spring.backend.entity.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IGenericService<S> {
	
	@Transactional(readOnly = true)
	public List<S> findAll();
	
	@Transactional(readOnly = true)
	public Page<S> findAll(Pageable p);
	
	
	@Transactional(readOnly = true)
	public S findById(Long id);
	
	@Transactional
	public S save(S entidad);
	
	@Transactional
	public void delete(Long id);
	
}
