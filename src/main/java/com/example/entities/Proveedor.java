package com.example.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedores") //expresamente quiero que la tabla tenga este nombre porque de lo contrario en mysql sale 
//el nombre de la clase en singular

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Proveedor implements Serializable{
    
    private static final long serialVersionUID = 1L; 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 

    private String nombre; 
    private String primerApellido; 
    private String segundoApellido; 

    private String telefono; 
    private String correo; 
    
    //@NotNull
    private String imagenProveedor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) 
    private Administrador administrador; 

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "proveedor") 
    private List<Producto> productos; 

}
