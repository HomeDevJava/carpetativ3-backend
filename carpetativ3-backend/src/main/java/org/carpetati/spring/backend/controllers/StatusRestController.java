package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.carpetati.spring.backend.entity.Status;
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
public class StatusRestController implements IEndPoints<Status>{
	
	@Autowired private IGenericService<Status> statusService;

	@GetMapping("/status")
	public List<Status> index() {
		return statusService.findAll();
	}
	
	@GetMapping("/status/page/{page}")
	public Page<Status> index(@PathVariable Integer page){
		return statusService.findAll(PageRequest.of(page, 10));
	}

	@GetMapping("/status/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Status status = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			status = statusService.findById(id);
			if (status == null) {
				response.put("msg", "el registro " + id + " no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(status, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta de la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/status")
	public ResponseEntity<?> insert(@Valid @RequestBody Status t, BindingResult result) {
		Status status = null;
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
			status = statusService.save(t);
			response.put("msg", "El registro status se ha creado con éxito con el ID: "+ status.getId());
			response.put("status", status);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch ( DataAccessException e) {			
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/status/{id}")
	public ResponseEntity<?> update(@RequestBody Status t, @PathVariable Long id) {
		
		Status statusActual = statusService.findById(id);
		Status statusUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (statusActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				// statusActual.ifPresent(e -> e.setNombre(entidad.getNombre()));
				t.setId(statusActual.getId());
				statusUpdated = statusService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+statusUpdated.getId());
				response.put("status", statusUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/status/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			statusService.delete(id);
			response.put("msg", "se ha eliminado el registro: ");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

}
