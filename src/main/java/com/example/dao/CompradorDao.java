package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Administrador;
import com.example.entities.Comprador;

public interface CompradorDao extends JpaRepository<Comprador, Long> {
    
    @Query(value = "select c from Comprador c left join fetch c.administrador")
    public List<Comprador> findAll(Sort sort); 

    @Query(value = "select c from Comprador c left join fetch c.administrador", 
    countQuery = "select count(c) from Comprador c left join c.administrador")
    public Page<Comprador> findAll(Pageable pageable); 

    @Query(value = "select c from Comprador c left join fetch c.administrador where c.id = :id") 
    public Comprador findById(long id);

    public List<Comprador> findByAdministrador(Administrador administrador); 
}   


