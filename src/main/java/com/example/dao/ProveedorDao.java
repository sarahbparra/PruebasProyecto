package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Proveedor;

public interface ProveedorDao extends JpaRepository<Proveedor, Long>{

    // @Query(value = "select p from proveedores p left join fetch p.administradores")
    public List<Proveedor> findAll(Sort sort); 

    // @Query(value = "select p from proveedores p left join fetch p.administradores", 
    // countQuery = "select count(p) from Proveedor p left join p.administradores")
    public Page<Proveedor> findAll(Pageable pageable); 

    // @Query(value = "select p  from proveedores p left join fetch p.administradores where p.id = :id") 
    public Proveedor findById(long id); 
    
}
