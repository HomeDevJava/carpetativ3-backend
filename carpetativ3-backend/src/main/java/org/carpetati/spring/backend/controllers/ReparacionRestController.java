package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.carpetati.spring.backend.entity.Reparacion;
import org.carpetati.spring.backend.entity.services.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
public class ReparacionRestController implements IEndPoints<Reparacion> {
	
	@Autowired private IGenericService<Reparacion> reparacionService;

	@GetMapping("/reparacion")
	public List<Reparacion> index() {		
		return reparacionService.findAll();
	}

	@GetMapping("/reparacion/{id}")
	public ResponseEntity<?> getOne(Long id) {
		Reparacion reparacion= null;
		Map<String, Object> response = new HashMap<>();
		try {
			reparacion = reparacionService.findById(id);
			if (reparacion == null) {
				response.put("msg", "el registro " + id + " no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(reparacion, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta de la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/reparacion")
	public ResponseEntity<?> insert(@Valid @RequestBody Reparacion t, BindingResult result) {
		
		Reparacion reparacion = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors= result.getFieldErrors()
					.stream()
					.map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			reparacion = reparacionService.save(t);
			response.put("msg", "El registro Reparacion se ha creado con éxito con el ID: "+ reparacion.getId());
			response.put("reparacion", reparacion);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch ( DataAccessException e) {			
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@PutMapping("/reparacion/{id}")
	public ResponseEntity<?> update(@RequestBody Reparacion t, @PathVariable Long id) {
		
		Reparacion reparacionActual = reparacionService.findById(id);
		Reparacion reparacionUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (reparacionActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				// ReparacionActual.ifPresent(e -> e.setNombre(entidad.getNombre()));
				t.setId(reparacionActual.getId());
				reparacionUpdated = reparacionService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+reparacionUpdated.getId());
				response.put("reparacion", reparacionUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/reparacion/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			reparacionService.delete(id);
			response.put("msg", "se ha eliminado el registro: ");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
