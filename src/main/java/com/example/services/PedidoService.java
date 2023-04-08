package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.entities.Pedido;

public interface PedidoService {
   
    public List<Pedido> findAll(Sort sort); 
    public Page<Pedido> findAll(Pageable pageable); 
    public Pedido findById(long id);
    public Pedido save(Pedido pedido); 
    public void delete(Pedido pedido);
    public String generarCodigo(); 
}
