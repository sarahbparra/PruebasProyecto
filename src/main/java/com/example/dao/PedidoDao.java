package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Pedido;

public interface PedidoDao extends JpaRepository<Pedido, Long> {

    @Query(value = "select p from Pedido p inner join fetch p.productos left join fetch p.comprador")
    public List<Pedido> findAll(Sort sort); 

    @Query(value = "select p from Pedido p left join fetch p.comprador", 
    countQuery = "select count(p) from Pedido p left join p.comprador")
    public Page<Pedido> findAll(Pageable pageable); 

    @Query(value = "select p from Pedido p left join fetch p.comprador where p.id = :id")
    public Pedido findById(long id); 

}
