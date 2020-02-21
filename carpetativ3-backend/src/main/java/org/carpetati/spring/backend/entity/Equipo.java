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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="equipos")
@Getter @Setter
public class Equipo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String serie;
	
	private String afijo;
	
	private Long enabled;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modelos_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	//@JsonManagedReference
	private Modelo modelo;

}
