package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.carpetati.spring.backend.entity.Equipo;
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
public class EquipoRestController implements IEndPoints<Equipo>{
	
	@Autowired private IGenericService<Equipo> equipoService;

	@GetMapping("/equipos")
	public List<Equipo> index() {
		return equipoService.findAll();
	}
	
	@GetMapping("/equipos/page/{page}")
	public Page<Equipo> index(@PathVariable Integer page){
		return equipoService.findAll(PageRequest.of(page, 10));
	}

	@GetMapping("/equipos/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Equipo equipo = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			equipo = equipoService.findById(id);
			if (equipo == null) {
				response.put("msg", "el registro " + id + " no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(equipo, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta de la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/equipos")
	public ResponseEntity<?> insert(@Valid @RequestBody Equipo t, BindingResult result) {
		Equipo equipo = null;
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
			equipo = equipoService.save(t);
			response.put("msg", "El registro equipo se ha creado con éxito con el ID: "+ equipo.getId());
			response.put("equipo", equipo);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch ( DataAccessException e) {			
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/equipos/{id}")
	public ResponseEntity<?> update(@RequestBody Equipo t, @PathVariable Long id) {
		
		Equipo equipoActual = equipoService.findById(id);
		Equipo equipoUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (equipoActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				// equipoActual.ifPresent(e -> e.setNombre(entidad.getNombre()));
				t.setId(equipoActual.getId());
				equipoUpdated = equipoService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+equipoUpdated.getId());
				response.put("equipo", equipoUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/equipos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			equipoService.delete(id);
			response.put("msg", "se ha eliminado el registro: ");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

}
