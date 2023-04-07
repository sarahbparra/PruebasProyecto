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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//No ponemos la anotacion @Table en ninguna clase entidad porq es un follon luego en la base de datos

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Proveedor implements Serializable{
    
    private static final long serialVersionUID = 1L; 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id; //cambios de int a long por si a caso ya que en el dao lo tenemos como Long

    private String nombre; 
    private String primerApellido; 
    private String segundoApellido; 

    private String telefono; 
    private String correo; 
    
    private String imagenProveedor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) 
//en la clase hijo Proveedor hacemos referencia al padre(Administrador) con el BackReference
    @JsonBackReference 
    private Administrador administrador; 

//ALL es porq cuando quiera borrar un proveedor, no me de problemas ya que no puedo borrar un padre y dejar 
//"huerfanos" a los hijos que en este caso no puedo borrar un proveedor sin borrar antes sus productos
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "proveedor") 
    private List<Producto> productos; 

}
