package com.example.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Proveedor implements Serializable{
    
    private static final long serialVersionUID = 1L; 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id; 

    private String nombre; 
    private String apellidos; 

    private String telefono; 
    private String correo; 
    
    private String documentacionProveedor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) 
    @JsonBackReference
    private Administrador administrador; 

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "proveedor") 
    private List<Producto> productos; 

}
