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
//import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movactivos")
@Getter
@Setter
// @NoArgsConstructor
public class Movactivo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "origen_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Cedi origen;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destino_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Cedi destino;

	private String motivo;
	private String caracteristica;

	//@Lob
	//private byte[] evidencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empleados_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Empleado empleado;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "movactivos_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JsonManagedReference
	private List<ItemMovactivo> items;

	public Movactivo() {
		this.items = new ArrayList<ItemMovactivo>();
	}

	public void AddItemMovactivo(ItemMovactivo item) {
		this.items.add(item);
	}

}
