package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Proveedor;

public interface ProveedorDao extends JpaRepository<Proveedor, Long>{

    // @Query(value = "select p from Proveedor p left join fetch p.administrador")
    //public List<Proveedor> findAll(Sort sort); 

    // @Query(value = "select p from Proveedor p left join fetch p.administrador", 
    // countQuery = "select count(p) from Proveedor p left join p.administrador")
    //public Page<Proveedor> findAll(Pageable pageable); 

    // @Query(value = "select p  from Proveedor p left join fetch p.administrador where p.id = :id") 
    // public Proveedor findById(long id); 
    
}
