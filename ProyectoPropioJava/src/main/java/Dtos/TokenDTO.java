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
	
	@JsonProperty("id_usuario")
	private long id_usuario;
	
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

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public TokenDTO(int idToken, String token, Calendar fch_limite, long id_usuario) {
		super();
		this.idToken = idToken;
		this.token = token;
		this.fch_limite = fch_limite;
		this.id_usuario = id_usuario;
	}
	public TokenDTO() {
		super();
		
	}

	public TokenDTO(String token, Calendar fch_limite, long id_usuario) {
		super();
		this.token = token;
		this.fch_limite = fch_limite;
		this.id_usuario = id_usuario;
	}
	
	
}
