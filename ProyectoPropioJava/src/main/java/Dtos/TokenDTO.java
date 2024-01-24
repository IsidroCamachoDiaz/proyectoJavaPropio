package Dtos;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;



public class TokenDTO {
	@JsonProperty("idToken")
	private int idToken;
	
	@JsonProperty("token")
	private String token;
		
	@JsonProperty("fch_limite")
	private Calendar fch_limite;
	
	@JsonProperty("usuario")
	private UsuarioDTO usuario;
	
	public int getIdToken() {
		return idToken;
	}

	public void setIdToken(int idToken) {
		this.idToken = idToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Calendar getFch_limite() {
		return fch_limite;
	}

	public void setFch_limite(Calendar fch_limite) {
		this.fch_limite = fch_limite;
	}

	public UsuarioDTO getId_usuario() {
		return usuario;
	}

	public void setId_usuario(UsuarioDTO id_usuario) {
		this.usuario = id_usuario;
	}
	
	public TokenDTO(int idToken, String token, Calendar fch_limite, UsuarioDTO id_usuario) {
		super();
		this.idToken = idToken;
		this.token = token;
		this.fch_limite = fch_limite;
		this.usuario = id_usuario;
	}
	public TokenDTO() {
		super();
		
	}

	public TokenDTO(String token, Calendar fch_limite, UsuarioDTO id_usuario) {
		super();
		this.token = token;
		this.fch_limite = fch_limite;
		this.usuario = id_usuario;
	}
	
	
}
