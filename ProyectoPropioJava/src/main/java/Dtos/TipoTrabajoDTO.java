package Dtos;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase que representa un tipo de trabajo con sus atributos y métodos asociados.
 * Esta clase es un Data Transfer Object (DTO) para transferir información de tipos de trabajo.
 * 
 * @author Isidro Camacho Diaz
 * 
 * @param id_tipo Identificador único del tipo de trabajo.
 * @param descripcion_tipo Descripción del tipo de trabajo.
 * @param precio_tipo Precio del tipo de trabajo.
 * @param fecha_fin Fecha de finalización del tipo de trabajo.
 */
public class TipoTrabajoDTO {
	
	//Propiedades
	@JsonProperty("id_tipo")
	private int id_tipo;
	
	@JsonProperty("descripcion_tipo")
	private String descripcion_tipo;
	
	@JsonProperty("precio_tipo")
	private float precio_tipo;
	
	@JsonProperty("fecha_fin")
	private Calendar fecha_fin;
	
	//Geters y Seters
	
	/**
	 * Obtiene la descripción del tipo de incidencia.
	 * @return La descripción del tipo de incidencia.
	 */
	public String getDescripcion_tipo() {
		return descripcion_tipo;
	}

	/**
	 * Establece la descripción del tipo de incidencia.
	 * @param descripcion_tipo La nueva descripción del tipo de incidencia.
	 */
	public void setDescripcion_tipo(String descripcion_tipo) {
		this.descripcion_tipo = descripcion_tipo;
	}

	/**
	 * Obtiene el precio del tipo de incidencia.
	 * @return El precio del tipo de incidencia.
	 */
	public float getPrecio_tipo() {
		return precio_tipo;
	}

	/**
	 * Obtiene la fecha de finalización del tipo de incidencia.
	 * @return La fecha de finalización del tipo de incidencia.
	 */
	public Calendar getFecha_fin() {
		return fecha_fin;
	}

	/**
	 * Establece la fecha de finalización del tipo de incidencia.
	 * @param fecha_fin La nueva fecha de finalización del tipo de incidencia.
	 */
	public void setFecha_fin(Calendar fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	/**
	 * Obtiene el ID del tipo de incidencia.
	 * @return El ID del tipo de incidencia.
	 */
	public int getId_tipo() {
		return id_tipo;
	}
	
	//Constructores
	
	/**
	 * Constructor de la clase TipoTrabajoDTO que inicializa la descripción y el precio del tipo de trabajo.
	 * @param descripcion_tipo La descripción del tipo de trabajo.
	 * @param precio_tipo El precio del tipo de trabajo.
	 */
	public TipoTrabajoDTO(String descripcion_tipo, float precio_tipo) {
		super();
		this.descripcion_tipo = descripcion_tipo;
		this.precio_tipo = precio_tipo;
	}


	/**
	 * Constructor de la clase TipoTrabajoDTO que inicializa todos los atributos del tipo de trabajo.
	 * @param id_tipo El ID del tipo de trabajo.
	 * @param descripcion_tipo La descripción del tipo de trabajo.
	 * @param precio_tipo El precio del tipo de trabajo.
	 * @param fecha_fin La fecha de finalización del tipo de trabajo.
	 */
	public TipoTrabajoDTO(int id_tipo, String descripcion_tipo, float precio_tipo, Calendar fecha_fin) {
		super();
		this.id_tipo = id_tipo;
		this.descripcion_tipo = descripcion_tipo;
		this.precio_tipo = precio_tipo;
		this.fecha_fin = fecha_fin;
	}

	/**
	 * Constructor de la clase TipoTrabajoDTO que inicializa los atributos por defecto.
	 */
	public TipoTrabajoDTO() {
		super();
	}
	
	
	
}
