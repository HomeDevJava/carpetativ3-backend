package org.carpetati.spring.backend.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name="empleados")
@Data
public class Empleado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String numempleado;
	
	private String nombre;
	
	private String apellidos;
	
	private String email;
	
	private String telefono;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cedis_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Cedi cedi;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="puestos_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Puesto puesto;

}
