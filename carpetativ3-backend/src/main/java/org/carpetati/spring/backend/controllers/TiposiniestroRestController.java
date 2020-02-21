package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.carpetati.spring.backend.entity.TipoSiniestro;
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
public class TiposiniestroRestController implements IEndPoints<TipoSiniestro> {

	@Autowired private IGenericService<TipoSiniestro> tiposiniestroService;

	@GetMapping("/tiposiniestro")
	public List<TipoSiniestro> index() {
		return tiposiniestroService.findAll();
	}
	
	@GetMapping("/tiposiniestro/page/{page}")
	public Page<TipoSiniestro> index(@PathVariable Integer page){
		return tiposiniestroService.findAll(PageRequest.of(page, 10));
	}

	@GetMapping("/tiposiniestro/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		TipoSiniestro tiposiniestro = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			tiposiniestro = tiposiniestroService.findById(id);
			if (tiposiniestro == null) {
				response.put("msg", "el registro " + id + " no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(tiposiniestro, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta de la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/tiposiniestro")
	public ResponseEntity<?> insert(@Valid @RequestBody TipoSiniestro t, BindingResult result) {
		TipoSiniestro tiposiniestro = null;
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
			tiposiniestro = tiposiniestroService.save(t);
			response.put("msg", "El registro tiposiniestro se ha creado con éxito con el ID: "+ tiposiniestro.getId());
			response.put("tiposiniestro", tiposiniestro);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch ( DataAccessException e) {			
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/tiposiniestro/{id}")
	public ResponseEntity<?> update(@RequestBody TipoSiniestro t, @PathVariable Long id) {
		
		TipoSiniestro tiposiniestroActual = tiposiniestroService.findById(id);
		TipoSiniestro tiposiniestroUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (tiposiniestroActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				// tiposiniestroActual.ifPresent(e -> e.setNombre(entidad.getNombre()));
				t.setId(tiposiniestroActual.getId());
				tiposiniestroUpdated = tiposiniestroService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+tiposiniestroUpdated.getId());
				response.put("tiposiniestro", tiposiniestroUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/tiposiniestro/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			tiposiniestroService.delete(id);
			response.put("msg", "se ha eliminado el registro: ");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
}
