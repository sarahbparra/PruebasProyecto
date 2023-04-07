package com.example.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Proveedor;

public interface ProveedorDao extends JpaRepository<Proveedor, Long>{

//Si no incluimos la siguiente Query, en el postman cuando le pasas el verbo/id te salta directamente
//el mensaje de error grave, es decir no entra al else y va directamente al catch

    @Query(value = "select p  from Proveedor p left join fetch p.administrador where p.id = :id") 
    public Proveedor findById(long id); 

















    

    // @Query(value = "select p from Proveedor p left join fetch p.administrador")
    // public List<Proveedor> findAll(Sort sort); 

    // @Query(value = "select p from Proveedor p left join fetch p.administrador", 
    // countQuery = "select count(p) from Proveedor p left join p.administrador")
    // public Page<Proveedor> findAll(Pageable pageable); 
    
}
