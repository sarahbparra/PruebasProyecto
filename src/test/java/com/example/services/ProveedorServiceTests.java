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
import com.example.dao.AdministradorDao;
import com.example.dao.ProveedorDao;
import com.example.entities.Administrador;
import com.example.entities.Proveedor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProveedorServiceTests {
    
    @Mock
    private ProveedorDao proveedorDao; 

    @Mock
    private AdministradorDao administradorDao; 

    @InjectMocks
    private ProveedorServiceImpl proveedorService; 

    private Proveedor proveedor; 

    @BeforeEach
    void setUp() {
        Administrador administrador = Administrador.builder()
            .nombre("Admin")
            .apellidos("Test Test")
            .correo("admintest@gmail.com")
            .telefono("666 666 666")
            .build();

            proveedor = Proveedor.builder()
            .nombre("Proveedor")
            .apellidos("Test Test")
            .telefono("666 666 666")
            .correo("proveedor@gmail.com")
            .documentacionProveedor(null)
            .administrador(administrador)
            .build(); 
    }

    @Test
    @DisplayName("Test de servicio para persistir un proveedor")
    public void testGuardarProveedor() {

        //given
        given(proveedorDao.save(proveedor)).willReturn(proveedor); 

        //when 
        Proveedor proveedorGuardado = proveedorService.save(proveedor); 

        //then 
        assertThat(proveedorGuardado).isNotNull(); 
    }

    @Test
    @DisplayName("Recupera una lista de compradores vacía")
    public void testListaVaciaProveedores() {

        //given 
        given(proveedorDao.findAll()).willReturn(Collections.emptyList()); 

        //when 
        List<Proveedor> proveedores = proveedorDao.findAll(); 

        //then 
        assertThat(proveedores).isEmpty();
    }
}
