package Dtos;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	public String getDescripcion_tipo() {
		return descripcion_tipo;
	}

	public void setDescripcion_tipo(String descripcion_tipo) {
		this.descripcion_tipo = descripcion_tipo;
	}

	public float getPrecio_tipo() {
		return precio_tipo;
	}


	public Calendar getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Calendar fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public int getId_tipo() {
		return id_tipo;
	}
	
	//Constructores
	public TipoTrabajoDTO(String descripcion_tipo, float precio_tipo) {
		super();
		this.descripcion_tipo = descripcion_tipo;
		this.precio_tipo = precio_tipo;
	}

	public TipoTrabajoDTO(int id_tipo, String descripcion_tipo, float precio_tipo, Calendar fecha_fin) {
		super();
		this.id_tipo = id_tipo;
		this.descripcion_tipo = descripcion_tipo;
		this.precio_tipo = precio_tipo;
		this.fecha_fin = fecha_fin;
	}

	public TipoTrabajoDTO() {
		super();
	}
	
	
	
}
