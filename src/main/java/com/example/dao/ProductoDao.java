package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Producto;

public interface ProductoDao extends JpaRepository<Producto, Long>{
    
   @Query(value = "select p from Producto p left join fetch p.proveedor")
//    @Query(value = "select p.* from producto p left join proveedor r on p.proveedor_id = r.id", nativeQuery = true)
    public List<Producto> findAll(Sort sort); 

    @Query(value = "select p from Producto p left join fetch p.proveedor", 
    countQuery = "select count(p) from Producto p left join p.proveedor")
    public Page<Producto> findAll(Pageable pageable);

    // @Query("select p from Producto p left join fetch p.proveedor where p.id = :id")
    // @Query(value = "select p.* from producto p left join proveedor r on p.proveedor_id = r.id and p.id = :id", nativeQuery = true)
    public Producto findById(long id); 
}
