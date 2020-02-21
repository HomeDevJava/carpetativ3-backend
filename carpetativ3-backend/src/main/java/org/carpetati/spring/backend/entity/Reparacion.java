package org.carpetati.spring.backend.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reparaciones")
@Getter @Setter
public class Reparacion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "se requiere la fecha de tramite")
	@Temporal(TemporalType.DATE) @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fectramite;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="status_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cedis_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Cedi cedi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empleados_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Empleado empleado;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="reparaciones_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JsonManagedReference
	private List<ItemReparacion> items;
	
	private String observacion;
	
	private String guiafedex;
	

	public Reparacion() {
		this.items = new ArrayList<ItemReparacion>();
	}
	
	public void AddItemReparacion(ItemReparacion item) {
		this.items.add(item);
	}
	
	

}
