package com.dimobys.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dimobys.springboot.app.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{
	
	//Busqueda por query
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
	
	//Busqueda por nombre de metodo
	public List<Producto> findByNombreLikeIgnoreCase(String term);
}
