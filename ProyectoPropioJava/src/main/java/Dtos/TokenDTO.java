package Dtos;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Clase que representa un token con sus atributos y métodos asociados.
 * Esta clase es un Data Transfer Object (DTO) para transferir información de tokens.
 * 
 * @author Isidro Camacho Diaz
 * 
 * @param idToken Identificador del token.
 * @param token Valor del token.
 * @param fch_limite Fecha límite del token.
 * @param usuario Objeto UsuarioDTO asociado al token.
 */ 
public class TokenDTO {
	@JsonProperty("idToken")
	private int idToken;
	
	@JsonProperty("token")
	private String token;
		
	@JsonProperty("fch_limite")
	private Calendar fch_limite;
	
	@JsonProperty("usuario")
	private UsuarioDTO usuario;
	
	/**
	 * Obtiene el ID del token.
	 * @return El ID del token.
	 */
	public int getIdToken() {
		return idToken;
	}

	/**
	 * Establece el ID del token.
	 * @param idToken El nuevo ID del token.
	 */
	public void setIdToken(int idToken) {
		this.idToken = idToken;
	}

	/**
	 * Obtiene el token.
	 * @return El token.
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Establece el token.
	 * @param token El nuevo token.
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Obtiene la fecha límite del token.
	 * @return La fecha límite del token.
	 */
	public Calendar getFch_limite() {
		return fch_limite;
	}

	/**
	 * Establece la fecha límite del token.
	 * @param fch_limite La nueva fecha límite del token.
	 */
	public void setFch_limite(Calendar fch_limite) {
		this.fch_limite = fch_limite;
	}

	/**
	 * Obtiene el usuario asociado al token.
	 * @return El usuario asociado al token.
	 */
	public UsuarioDTO getId_usuario() {
		return usuario;
	}

	/**
	 * Establece el usuario asociado al token.
	 * @param id_usuario El nuevo usuario asociado al token.
	 */
	public void setId_usuario(UsuarioDTO id_usuario) {
		this.usuario = id_usuario;
	}
	
	//Constructores
	
	/**
	 * Constructor para crear un objeto TokenDTO con todos los atributos.
	 * 
	 * @param idToken Identificador del token.
	 * @param token Valor del token.
	 * @param fch_limite Fecha límite del token.
	 * @param id_usuario Objeto UsuarioDTO asociado al token.
	 */
	public TokenDTO(int idToken, String token, Calendar fch_limite, UsuarioDTO id_usuario) {
	    super();
	    this.idToken = idToken;
	    this.token = token;
	    this.fch_limite = fch_limite;
	    this.usuario = id_usuario;
	}

	/**
	 * Constructor predeterminado para crear un objeto TokenDTO sin atributos.
	 */
	public TokenDTO() {
	    super();
	}

	/**
	 * Constructor para crear un objeto TokenDTO con algunos atributos.
	 * 
	 * @param token Valor del token.
	 * @param fch_limite Fecha límite del token.
	 * @param id_usuario Objeto UsuarioDTO asociado al token.
	 */
	public TokenDTO(String token, Calendar fch_limite, UsuarioDTO id_usuario) {
	    super();
	    this.token = token;
	    this.fch_limite = fch_limite;
	    this.usuario = id_usuario;
	}

	
	
}
