package com.example.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L; 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id; 

    private String codigoPedido; 
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaPedido; 

    private BigDecimal costeFinal;

    // public void calcularCosteFinal() {
    //     BigDecimal total = BigDecimal.ZERO;
    //     for (Producto producto : productos) {
    //         total = total.add(producto.getPrecio()); 
    //     }
    //     this.costeFinal = total;
    // }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "pedido") 
    @JsonManagedReference
    private List<Producto> productos; 

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Comprador comprador; 

    
}
