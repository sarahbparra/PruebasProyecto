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
import com.example.entities.Administrador;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class AdministradorServiceTests {
    
    @Mock
    private AdministradorDao administradorDao; 

    @InjectMocks
    private AdministradorServiceImpl administradorService; 

    private Administrador administrador; 

    @BeforeEach
    void setUp() {

        administrador = Administrador.builder()
        .nombre("Admin")
        .apellidos("Test Test")
        .correo("admintest@gmail.com")
        .telefono("666 666 666")
        .build();
    }

    @Test
    @DisplayName("Test de servicio para persistir un administador")
    public void testGuardarAdministrador() {

        //given 
        given(administradorDao.save(administrador)).willReturn(administrador); 

        //when 
        Administrador administradorGuardado = administradorService.save(administrador); 

        //then 
        assertThat(administradorGuardado).isNotNull(); 
    }

    @Test
    @DisplayName("Test para recuperar una lista vacía de administradores")
    public void testListaVaciaAdmin() {

        //given 
        given(administradorDao.findAll()).willReturn(Collections.emptyList()); 

        //when 
        List<Administrador> administradores = administradorService.findAll(); 

        //then 
        assertThat(administradores).isEmpty();
    }
}
