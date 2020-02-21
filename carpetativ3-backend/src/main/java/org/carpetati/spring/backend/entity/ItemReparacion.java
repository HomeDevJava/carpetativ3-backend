package org.carpetati.spring.backend.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="reparaciones_items")
@Getter @Setter
public class ItemReparacion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reparaciones_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JsonBackReference
	private Reparacion reparacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipos_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Equipo equipo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoproblemas_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private TipoProblema tipoproblema;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecenvio;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecretorno;
	
	private String falla;

}
