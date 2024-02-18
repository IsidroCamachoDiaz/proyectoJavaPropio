package Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase que representa un trabajo con sus atributos y métodos asociados.
 * Esta clase es un Data Transfer Object (DTO) para transferir información de trabajos.
 * 
 * @author Isidro Camacho Diaz
 * 
 * @param id_trabajo Identificador del trabajo.
 * @param descripcion Descripción del trabajo.
 * @param estado Estado del trabajo (true si está completado, false si no está completado).
 * @param horas Horas dedicadas al trabajo.
 * @param incidencia Objeto IncidenciaDTO asociado al trabajo.
 * @param tipoIncidencia Objeto TipoTrabajoDTO asociado al tipo de incidencia.
 */ 
public class TrabajoDTO {
	
	//Atributos

	@JsonProperty("id_trabajo")
	private int id_trabajo;
	
	@JsonProperty("descripcion")
	private String descripcion;
	
	@JsonProperty("estado")
	private boolean estado;
	
	@JsonProperty("horas")
	private int horas;
	
	@JsonProperty("incidencia")
	private IncidenciaDTO incidencia;
	
	@JsonProperty("tipoIncidencia")
	private TipoTrabajoDTO tipoIncidencia;
	
	//Constructores

	/**
	 * Constructor para crear un objeto TrabajoDTO con todos los atributos.
	 * 
	 * @param descripcion Descripción del trabajo.
	 * @param estado Estado del trabajo.
	 * @param horas Horas dedicadas al trabajo.
	 * @param incidencia Objeto IncidenciaDTO asociado al trabajo.
	 * @param tipoIncidencia Objeto TipoTrabajoDTO asociado al tipo de incidencia.
	 */
	public TrabajoDTO(String descripcion, boolean estado, int horas, IncidenciaDTO incidencia,
	        TipoTrabajoDTO tipoIncidencia) {
	    super();
	    this.descripcion = descripcion;
	    this.estado = estado;
	    this.horas = horas;
	    this.incidencia = incidencia;
	    this.tipoIncidencia = tipoIncidencia;
	}

	/**
	 * Constructor para crear un objeto TrabajoDTO con todos los atributos.
	 * 
	 * @param id_trabajo Identificador del trabajo.
	 * @param descripcion Descripción del trabajo.
	 * @param estado Estado del trabajo.
	 * @param horas Horas dedicadas al trabajo.
	 * @param incidencia Objeto IncidenciaDTO asociado al trabajo.
	 * @param tipoIncidencia Objeto TipoTrabajoDTO asociado al tipo de incidencia.
	 */
	public TrabajoDTO(int id_trabajo, String descripcion, boolean estado, int horas, IncidenciaDTO incidencia,
	        TipoTrabajoDTO tipoIncidencia) {
	    super();
	    this.id_trabajo = id_trabajo;
	    this.descripcion = descripcion;
	    this.estado = estado;
	    this.horas = horas;
	    this.incidencia = incidencia;
	    this.tipoIncidencia = tipoIncidencia;
	}

	/**
	 * Constructor predeterminado para crear un objeto TrabajoDTO sin atributos.
	 */
	public TrabajoDTO() {
	    super();
	}

	
	//Geters y Seters

	public int getId_trabajo() {
		return id_trabajo;
	}

	public void setId_trabajo(int id_trabajo) {
		this.id_trabajo = id_trabajo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public IncidenciaDTO getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(IncidenciaDTO incidencia) {
		this.incidencia = incidencia;
	}

	public TipoTrabajoDTO getTipoIncidencia() {
		return tipoIncidencia;
	}

	public void setTipoIncidencia(TipoTrabajoDTO tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}
	
	
	
}
