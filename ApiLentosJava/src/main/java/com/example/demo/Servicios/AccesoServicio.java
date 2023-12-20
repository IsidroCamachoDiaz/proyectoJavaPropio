package com.example.demo.Servicios;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Modelos.Acceso;
import com.example.demo.Modelos.AccesoRepositorio;
/**
 * Clase para establecer el accesos a la base de datos y obtener información o modificar, mediante los controladores
 * @author El Equipazo de los Lentos
 */
@Service
public class AccesoServicio {

	@Autowired
	AccesoRepositorio accesoRepositorio;
	
	/**
	 * Metodo que devuelve todos los acceso
	 * @return Iterable<Acceso>
	 * 
	 */
	public Iterable<Acceso> getAcceso()
	{
		return accesoRepositorio.findAll();
	}
	
	/**
	 * Metodo para insertar acceso
	 * @param Acceso acceso
	 * 
	 */
	public void insertarAcceso(Acceso acceso)
	{
		accesoRepositorio.save(acceso);
	}
	/**
	 * Metodo que devuelve un acceso espeficico
	 * @param Long id
	 * @return Optional<Acceso>
	 * 
	 */
	public Optional<Acceso> getAccesoId(Long id)
	{
		return accesoRepositorio.findById(id);
	}
	
	/**
	 * Metodo para actualizar un acceso
	 * @param Long id
	 * @param Acceso acceso
	 * 
	 */
	public void updateAcceso(Acceso acceso,Long id)
	{
		Acceso accesoObetnido=accesoRepositorio.findById(id).get();
		accesoObetnido.setCodigoAcceso(acceso.getCodigoAcceso());
		accesoObetnido.setDescripcionAcceso(acceso.getDescripcionAcceso());
		accesoRepositorio.save(accesoObetnido);
	}
	/**
	 * Metodo para eliminar un acceso
	 * @param Long id
	 * @return Boolean 
	 * 
	 */
	public Boolean deleteAcceso(Long id) {
		try {
			accesoRepositorio.deleteById(id);
		}catch(IllegalArgumentException e)
		{
			return false;
		}
		return true;
		
	}
}
