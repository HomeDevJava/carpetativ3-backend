package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.carpetati.spring.backend.entity.Marca;
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
public class MarcaRestController implements IEndPoints<Marca>{
	
	@Autowired private IGenericService<Marca> marcaService;

	@GetMapping("/marca")
	public List<Marca> index() {
		return marcaService.findAll();
	}
	
	@GetMapping("/marca/page/{page}")
	public Page<Marca> index(@PathVariable Integer page){
		return marcaService.findAll(PageRequest.of(page, 10));
	}

	@GetMapping("/marca/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Marca marca = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			marca = marcaService.findById(id);
			if (marca == null) {
				response.put("msg", "el registro " + id + " no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(marca, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta de la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/marca")
	public ResponseEntity<?> insert(@Valid @RequestBody Marca t, BindingResult result) {
		Marca marca = null;
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
			marca = marcaService.save(t);
			response.put("msg", "El registro marca se ha creado con éxito con el ID: "+ marca.getId());
			response.put("marca", marca);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch ( DataAccessException e) {			
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/marca/{id}")
	public ResponseEntity<?> update(@RequestBody Marca t, @PathVariable Long id) {
		
		Marca marcaActual = marcaService.findById(id);
		Marca marcaUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (marcaActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				// marcaActual.ifPresent(e -> e.setNombre(entidad.getNombre()));
				t.setId(marcaActual.getId());
				marcaUpdated = marcaService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+marcaUpdated.getId());
				response.put("marca", marcaUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/marca/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			marcaService.delete(id);
			response.put("msg", "se ha eliminado el registro con exito: ");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

}
