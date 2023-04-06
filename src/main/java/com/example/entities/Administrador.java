package com.example.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administradores")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Administrador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 25, message = "El nombre tiene que estar entre 4 y 25 caracteres")
       private String nombre;

    private String apellidos;

    @NotNull
    private String correo;

    private String telefono;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "administrador")
    private List<Comprador> compradores;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "administrador")
    private List<Proveedor> proveedores;

    public Administrador orElse(Object object) {
        return null;
    }

}
