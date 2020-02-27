package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.carpetati.spring.backend.entity.Movactivo;
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
public class MovactivoRestController implements IEndPoints<Movactivo> {
	
	@Autowired private IGenericService<Movactivo> movactivoService;

	@GetMapping("/movactivo")
	public List<Movactivo> index() {		
		return movactivoService.findAll();
	}

	@GetMapping("/movactivo/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {		
		Movactivo movactivo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			movactivo = movactivoService.findById(id);
			if (movactivo == null) {
				response.put("msg", "El registro "+id+" no existe en la BD");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(movactivo, HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta en la BD");
			response.put("eror", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/movactivo")
	public ResponseEntity<?> insert(@Valid @RequestBody Movactivo t, BindingResult result) {
		Movactivo movactivo = null;
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
			movactivo = movactivoService.save(t);
			response.put("msg", "El registro Movactivo se ha creado con éxito con el ID: "+ movactivo.getId());
			response.put("reparacion", movactivo);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@PutMapping("/movactivo/{id}")
	public ResponseEntity<?> update(@RequestBody Movactivo t, @PathVariable Long id) {
		Movactivo movactivoActual = movactivoService.findById(id);
		Movactivo movactivoUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			if (movactivoActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				t.setId(movactivoActual.getId());
				movactivoUpdated = movactivoService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+movactivoUpdated.getId());
				response.put("reparacion", movactivoUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}
		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
	}

	@DeleteMapping("/movactivo/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			movactivoService.delete(id);
			response.put("msg", "se ha eliminado el registro: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar Registro Movactivo en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
