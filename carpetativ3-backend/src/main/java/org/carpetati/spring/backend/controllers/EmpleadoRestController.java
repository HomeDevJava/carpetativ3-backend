package org.carpetati.spring.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.carpetati.spring.backend.entity.Empleado;
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
public class EmpleadoRestController implements IEndPoints<Empleado> {
	
	@Autowired private IGenericService<Empleado> empleadoService;
	
	@GetMapping("/empleado")
	public List<Empleado> index(){
		return empleadoService.findAll();
	}
	
	@GetMapping("/empleado/page/{page}")
	public Page<Empleado> index(@PathVariable Integer page){
		return empleadoService.findAll(PageRequest.of(page, 10));
	}


	@GetMapping("/empleado/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Empleado empleado = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			empleado = empleadoService.findById(id);
			if (empleado == null) {
				response.put("msg", "el registro " + id + " no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(empleado, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar la consulta de la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/empleado")
	public ResponseEntity<?> insert(@Valid @RequestBody Empleado t, BindingResult result) {
		Empleado empleado = null;
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
			empleado = empleadoService.save(t);
			response.put("msg", "El registro empleado se ha creado con éxito con el ID: "+ empleado.getId());
			response.put("empleado", empleado);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch ( DataAccessException e) {			
			response.put("msg", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/empleado/{id}")
	public ResponseEntity<?> update(@RequestBody Empleado t, @PathVariable Long id) {
		
		Empleado empleadoActual = empleadoService.findById(id);
		Empleado empleadoUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (empleadoActual == null) {
				response.put("msg", "el registro con ID:" + id.toString() + "no existe en la Base de Datos");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				// empleadoActual.ifPresent(e -> e.setNombre(entidad.getNombre()));
				t.setId(empleadoActual.getId());
				empleadoUpdated = empleadoService.save(t);
				response.put("msg", "se ha actualizado el registro con ID: "+empleadoUpdated.getId());
				response.put("empleado", empleadoUpdated);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}

		} catch (DataAccessException e) {
			response.put("msg", "Error al realizar el update en la BD: " + e.getMessage());
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/empleado/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			empleadoService.delete(id);
			response.put("msg", "se ha eliminado el registro: ");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("msg", "Error al eliminar en la BD");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

}
