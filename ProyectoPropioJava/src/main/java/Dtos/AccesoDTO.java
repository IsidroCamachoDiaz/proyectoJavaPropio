package Dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author El Equipazo de los Lentos
 * 
 * @param idAccesoDto Identificador de Acceso
 * @param codigoAccesoDto El Codigo de Acceso
 * @param descripcionAcceso Descripcion del tipo de Acceso
 * @param  listaUsuariosConAccesoDto Lista de todos los usuarios con Acceso
 */
public class AccesoDTO {
    @JsonProperty("idAcceso")
    private int idAccesoDto;
    @JsonProperty("codigoAcceso")
    private String codigoAccesoDto;
    @JsonProperty("descripcionAcceso")
    private String descripcionAccesoDto;
    @JsonProperty("usuariosConAcceso")
    private List<UsuarioDTO> listaUsuariosConAccesoDto;

    /**
     * Constructor por defecto
     */
    public AccesoDTO() {
        super();
    }
    
    
    /**
     * Constructor con los datos del Id, Codigo y Descripcion
     * 
     * @param idAccesoDto id del Acceso
     * @param codigoAccesoDto codigo de Acceso
     * @param descripcionAccesoDto Descripcion del Acceso 
     */
    public AccesoDTO(int idAccesoDto, String codigoAccesoDto, String descripcionAccesoDto) {
		super();
		this.idAccesoDto = idAccesoDto;
		this.codigoAccesoDto = codigoAccesoDto;
		this.descripcionAccesoDto = descripcionAccesoDto;
	}


    /**
     * Constructor con los datos codigo y descrpcion
     * @param codigoAcceso codigo de Acceso
     * @param descripcionAcceso descripcion del Acceso 
     */
	public AccesoDTO(String codigoAcceso, String descripcionAcceso) {
        super();
        this.codigoAccesoDto = codigoAcceso;
        this.descripcionAccesoDto = descripcionAcceso;
    }

	/**
	 * Devuelve el id del Acceso 
	 * @return devuelve id de Acceso
	 */
    public int getIdAcceso() {
        return idAccesoDto;
    }

    /**
     * Inserta un nuevo idAcceos
     * @param idAcceso id del Acceso
     */
    public void setIdAcceso(int idAcceso) {
        this.idAccesoDto = idAcceso;
    }

    /**
     * Devuelve el codigo de Acceso
     * @return codigo de Acceso
     */
    public String getCodigoAcceso() {
        return codigoAccesoDto;
    }
}   
