package com.example.services;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.PedidoDao;
import com.example.entities.Pedido;

@Service

public class PedidoServiceImpl implements PedidoService{

   @Autowired
   private PedidoDao pedidoDao; 

    @Override
    public List<Pedido> findAll(Sort sort) {
        
        return pedidoDao.findAll(sort); 
    }

    @Override
    public Page<Pedido> findAll(Pageable pageable) {
        
        return pedidoDao.findAll(pageable); 
    }

    @Override
    public Pedido findById(long id) {
        
        return pedidoDao.findById(id); 
    }

    @Override
    @Transactional
    public Pedido save(Pedido pedido) {
        
        return pedidoDao.save(pedido); 
    }

    @Override
    @Transactional
    public void delete(Pedido pedido) {
        
        pedidoDao.delete(pedido);
    }

    @Override
    public String generarCodigo() {
        
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        String codigo; 
        
        codigo = RandomStringUtils.random(6, caracteres);
    
        return codigo;

        // return codigo = RandomStringUtils.random(6, caracteres); 

    }
    
}
