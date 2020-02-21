package org.carpetati.spring.backend.entity.services;

import java.util.List;
import org.carpetati.spring.backend.entity.Device;
import org.carpetati.spring.backend.entity.dao.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = "deviceService")
public class DeviceService implements IGenericService<Device> {

	@Autowired private DeviceRepository deviceDao;

	@Override
	public List<Device> findAll() {
		return deviceDao.findAll();
	}

	@Override
	public Device findById(Long id) {
		return deviceDao.findById(id).orElse(null);
	}

	@Override
	public Device save(Device entidad) {
		return deviceDao.save(entidad);
	}

	@Override
	public void delete(Long id) {
		deviceDao.deleteById(id);		
	}

	@Override
	public Page<Device> findAll(Pageable p) {
		return deviceDao.findAll(p);
	}

}