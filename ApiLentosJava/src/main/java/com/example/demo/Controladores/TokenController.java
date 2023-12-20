package com.example.demo.Controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Servicios.TokenServicio;

import com.example.demo.Modelos.Token;
import com.example.demo.Modelos.Usuario;

@RestController
@RequestMapping("/token")
public class TokenController {
	
	@Autowired
	private TokenServicio tokenServicio;
	
	@GetMapping(path="/tokenSelect/{token}")
	public Optional<Token> getToken(@PathVariable("token") String token) {	
		return this.tokenServicio.getToken(token);
	}
	
	@PostMapping("/tokenInsertar")
	public void insertarToken(@RequestBody Token token) {
		this.tokenServicio.insertarToken(token);
	}
}
