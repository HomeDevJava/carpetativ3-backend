package org.carpetati.spring.backend.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="cedis")
@Data
public class Cedi implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "el campo 'nombre' no puede ser vacio")
	@Column(nullable = false)
	private String nombre;
	
	@NotNull(message = "el campo 'UOP' no puede ser vacio")
	@Column(nullable = false, unique = true)
	private Long uop;
	
	@NotNull(message = "el campo 'domicilio' no puede ser vacio")
	@Column(nullable = false)
	private String domicilio;
	
	@NotNull(message = "el campo 'telefono' no puede ser vacio")
	@Size(min = 6, max = 10, message ="el campo 'telefono' debe contener 6 a 10 digitos" )
	@Column(nullable = false)
	private String telefono;
	
}