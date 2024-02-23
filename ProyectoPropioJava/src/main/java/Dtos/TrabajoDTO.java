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
	
	/**
	 * Obtiene el ID del trabajo.
	 * @return El ID del trabajo.
	 */
	public int getId_trabajo() {
		return id_trabajo;
	}

	/**
	 * Establece el ID del trabajo.
	 * @param id_trabajo El nuevo ID del trabajo.
	 */
	public void setId_trabajo(int id_trabajo) {
		this.id_trabajo = id_trabajo;
	}

	/**
	 * Obtiene la descripción del trabajo.
	 * @return La descripción del trabajo.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del trabajo.
	 * @param descripcion La nueva descripción del trabajo.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Comprueba si el trabajo está en estado.
	 * @return true si el trabajo está en estado, false de lo contrario.
	 */
	public boolean isEstado() {
		return estado;
	}

	/**
	 * Establece el estado del trabajo.
	 * @param estado El nuevo estado del trabajo.
	 */
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene las horas del trabajo.
	 * @return Las horas del trabajo.
	 */
	public int getHoras() {
		return horas;
	}

	/**
	 * Establece las horas del trabajo.
	 * @param horas Las nuevas horas del trabajo.
	 */
	public void setHoras(int horas) {
		this.horas = horas;
	}

	/**
	 * Obtiene la incidencia asociada al trabajo.
	 * @return La incidencia asociada al trabajo.
	 */
	public IncidenciaDTO getIncidencia() {
		return incidencia;
	}

	/**
	 * Establece la incidencia asociada al trabajo.
	 * @param incidencia La nueva incidencia asociada al trabajo.
	 */
	public void setIncidencia(IncidenciaDTO incidencia) {
		this.incidencia = incidencia;
	}
	
	/**
	 * Obtiene el tipo de incidencia del trabajo.
	 * @return El tipo de incidencia del trabajo.
	 */
	public TipoTrabajoDTO getTipoIncidencia() {
		return tipoIncidencia;
	}

	/**
	 * Establece el tipo de incidencia del trabajo.
	 * @param tipoIncidencia El nuevo tipo de incidencia del trabajo.
	 */
	public void setTipoIncidencia(TipoTrabajoDTO tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}
	
	
	
}
