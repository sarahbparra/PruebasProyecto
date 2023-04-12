package com.example.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.dao.ProductoDao;
import com.example.dao.ProveedorDao;
import com.example.entities.Producto;
// import com.example.entities.Proveedor;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class ProductoServiceTests {

    @Mock
    private ProductoDao productoDao; 

    @Mock
    private ProveedorDao proveedorDao;

    @InjectMocks
    private ProductoServiceImpl productoService; 

    private Producto producto; 

    @BeforeEach 
    void setUp() {
        // Proveedor proveedor = Proveedor.builder()
        // .nombre("Proveedor")
        // .apellidos("Test Test")
        // .correo("proveedor@gg.com")
        // .telefono("111 222 333")
        // .build(); 

        producto = Producto.builder()
        .nombre("Queso Test")
        .descripcion("test")
        .procedencia("prueba")
        .precio("00.00€")
        .peso(99.99)
        .volumen(99.99)
        .build(); 
    }

    @Test
    @DisplayName("Test de servicio para dar de alta un producto")
    public void testGuardarProducto() {

        //given 
        given(productoDao.save(producto)).willReturn(producto); 

        //when
        Producto productoGuardado = productoService.save(producto); 

        //then 
        assertThat(productoGuardado).isNotNull(); 
    }

    @DisplayName("Recupera una lista vacía de productos")
    @Test
    public void testListaVacia() {

        //given 
        given(productoDao.findAll()).willReturn(Collections.emptyList()); 

        //when 
        List<Producto> productos = productoDao.findAll(); 

        //then 
        assertThat(productos).isEmpty();

        
    }

}
