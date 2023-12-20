package com.example.demo.Modelos;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="token")
public class Token {
	
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_token", nullable=false)
	private long idToken;
		
	@Column(name="token", nullable=false)
	private String token;
		
	@Column(name="fch_limite")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fch_limite;
	
	// GETTER / SETTER
	
	public long getIdToken() {
		return idToken;
	}

	public void setIdToken(long idToken) {
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
	// CONSTRUTORES
	public Token() {
		super();
	}

	public Token(String token, Calendar fch_limite) {
		this.token = token;
		this.fch_limite = fch_limite;
	}
}
