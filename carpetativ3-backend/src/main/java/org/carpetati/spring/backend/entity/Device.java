package org.carpetati.spring.backend.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "devices")
public class Device implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "el campo 'nombre' no puede ser vacio")
	@Size(min = 2)
	private String nombre;
	
	//@JsonBackReference
	@OneToMany(mappedBy="device",cascade=CascadeType.ALL, fetch=FetchType.LAZY)	
	@JsonIgnore
	private List<Modelo> modelo;
	

}