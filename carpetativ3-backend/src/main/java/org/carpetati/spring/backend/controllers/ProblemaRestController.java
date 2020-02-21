package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.carpetati.spring.backend.entity.TipoProblema;
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
public class ProblemaRestController implements IEndPoints<TipoProblema>{
	
	@Autowired private IGenericService<TipoProblema> tipoproblemaService;

	@GetMapping("/tipoproblema")
	public List<TipoProblema> index() {
		return tipoproblemaService.findAll();
	}
	
	@GetMapping("/tipoproblema/page/{page}")
	public Page<TipoProblema> index(@PathVariable Integer page){
		return tipoproblemaService.findAll(PageRequest.of(page, 10));
	}

	@GetMapping("/tipoproblema/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		TipoProblema tipoproblema = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			tipoproblema = tipoproblemaService.findById(id);
			if (tipoproblema == null) {
				response.put("msg", "el registro " + id + " no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(tipoproblema, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta de la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/tipoproblema")
	public ResponseEntity<?> insert(@Valid @RequestBody TipoProblema t, BindingResult result) {
		TipoProblema tipoproblema = null;
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
			tipoproblema = tipoproblemaService.save(t);
			response.put("msg", "El registro tipoproblema se ha creado con éxito con el ID: "+ tipoproblema.getId());
			response.put("tipoproblema", tipoproblema);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch ( DataAccessException e) {			
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/tipoproblema/{id}")
	public ResponseEntity<?> update(@RequestBody TipoProblema t, @PathVariable Long id) {
		
		TipoProblema tipoproblemaActual = tipoproblemaService.findById(id);
		TipoProblema tipoproblemaUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (tipoproblemaActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				// tipoproblemaActual.ifPresent(e -> e.setNombre(entidad.getNombre()));
				t.setId(tipoproblemaActual.getId());
				tipoproblemaUpdated = tipoproblemaService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+tipoproblemaUpdated.getId());
				response.put("tipoproblema", tipoproblemaUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/tipoproblema/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			tipoproblemaService.delete(id);
			response.put("msg", "se ha eliminado el registro: ");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

}
