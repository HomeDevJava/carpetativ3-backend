package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.ItemReparacion;
import org.carpetati.spring.backend.entity.dao.ItemReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemReparacionService implements IGenericService<ItemReparacion> {
	
	@Autowired ItemReparacionRepository itemReparacionDao;

	@Override
	public List<ItemReparacion> findAll() {		
		return itemReparacionDao.findAll();
	}

	@Override
	public Page<ItemReparacion> findAll(Pageable p) {		
		return itemReparacionDao.findAll(p);
	}

	@Override
	public ItemReparacion findById(Long id) {		
		return itemReparacionDao.findById(id).orElse(null);
	}

	@Override
	public ItemReparacion save(ItemReparacion entidad) {		
		return itemReparacionDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		itemReparacionDao.deleteById(id);		
	}

}
