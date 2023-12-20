package com.example.demo.Servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Modelos.Acceso;
import com.example.demo.Modelos.Token;
import com.example.demo.Modelos.TokenRepositorio;
import com.example.demo.Modelos.Usuario;

@Service
public class TokenServicio {
	@Autowired
	TokenRepositorio tokenRepositorio;
	
	public Optional<Token> getToken(String token)
	{
		return tokenRepositorio.findByToken(token);
	}
	
	public void insertarToken(Token token)
	{
		tokenRepositorio.save(token);
	}
}
