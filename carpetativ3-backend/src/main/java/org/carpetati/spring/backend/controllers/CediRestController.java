package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.carpetati.spring.backend.entity.Cedi;
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
public class CediRestController implements IEndPoints<Cedi> {

	@Autowired private IGenericService<Cedi> cediService;
	
	@GetMapping("/cedi")
	public List<Cedi> index(){
		return cediService.findAll();
	}
	
	@GetMapping("/cedi/page/{page}")
	public Page<Cedi> index(@PathVariable Integer page){
		return cediService.findAll(PageRequest.of(page, 10));
	}

	@GetMapping("/cedi/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Cedi cedi = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			cedi = cediService.findById(id);
			if (cedi == null) {
				response.put("msg", "el registro " + id + " no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(cedi, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta de la BD: " + e.getMessage());
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/cedi")
	public ResponseEntity<?> insert(@Valid @RequestBody Cedi t, BindingResult result) {
		Cedi cedi = null;
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
			cedi = cediService.save(t);
			response.put("msg", "El registro CEDI se ha creado con éxito con el ID: "+ cedi.getId());
			response.put("cedi", cedi);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch ( DataAccessException e) {			
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/cedi/{id}")
	public ResponseEntity<?> update(@RequestBody Cedi t, @PathVariable Long id) {
		
		Cedi cediActual = cediService.findById(id);
		Cedi cediUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (cediActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				// cediActual.ifPresent(e -> e.setNombre(entidad.getNombre()));
				t.setId(cediActual.getId());
				cediUpdated = cediService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+cediUpdated.getId());
				response.put("cedi", cediUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/cedi/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			cediService.delete(id);
			response.put("msg", "se ha eliminado el registro: ");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
}
