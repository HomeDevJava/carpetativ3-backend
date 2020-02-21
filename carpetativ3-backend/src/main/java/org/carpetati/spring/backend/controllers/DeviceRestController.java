package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.carpetati.spring.backend.entity.Device;
import org.carpetati.spring.backend.entity.services.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
@RestController
@RequestMapping(path = "/api")
public class DeviceRestController implements IEndPoints<Device> {
	
	@Autowired private IGenericService<Device> deviceService;
	
	@GetMapping("/device")
	public List<Device> index(){
		return deviceService.findAll();
	}
	
	@GetMapping("/device/page/{page}")
	public Page<Device> index(@PathVariable Integer page){
		return deviceService.findAll(PageRequest.of(page, 10));
	}

	@GetMapping("/device/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Device device = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			device = deviceService.findById(id);
			if (device == null) {
				response.put("msg", "el registro " + id + " no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(device, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta de la BD" );
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/device")
	public ResponseEntity<?> insert(@Valid @RequestBody Device t, BindingResult result) {
		Device device = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		
		if(result.hasErrors()) {
			List<String> errors= result.getFieldErrors()
					.stream()
					.map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			device = deviceService.save(t);
			response.put("msg", "El registro device se ha creado con éxito con el ID: "+ device.getId());
			response.put("device", device);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch ( DataAccessException e) {			
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/device/{id}")
	public ResponseEntity<?> update(@RequestBody Device t, @PathVariable Long id) {
		
		Device deviceActual = deviceService.findById(id);
		Device deviceUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (deviceActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				// deviceActual.ifPresent(e -> e.setNombre(entidad.getNombre()));
				t.setId(deviceActual.getId());
				deviceUpdated = deviceService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+deviceUpdated.getId());
				response.put("device", deviceUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/device/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			deviceService.delete(id);
			response.put("msg", "se ha eliminado el registro: ");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar en la BD: " + e.getMessage());
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	
}