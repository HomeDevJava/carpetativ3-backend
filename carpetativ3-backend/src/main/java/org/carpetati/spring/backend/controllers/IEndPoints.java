package org.carpetati.spring.backend.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IEndPoints<T> {
		
	public List<T> index();
	
	public ResponseEntity<?> getOne(Long id);
	
	public ResponseEntity<?> insert( T t, BindingResult r);
	
	public ResponseEntity<?> update(T t, Long id);
	
	public ResponseEntity<?> delete(Long id);

}
