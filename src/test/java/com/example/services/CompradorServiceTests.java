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
import com.example.dao.CompradorDao;
import com.example.entities.Administrador;
import com.example.entities.Comprador;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CompradorServiceTests {

    @Mock 
    private AdministradorDao administradorDao; 

    @Mock
    private CompradorDao compradorDao; 

    @InjectMocks 
    private CompradorServiceImpl compradorService; 

    private Comprador comprador; 

    @BeforeEach
    void setUp(){

        Administrador administrador = Administrador.builder()
            .nombre("Admin")
            .apellidos("Test Test")
            .correo("admintest@gmail.com")
            .telefono("666 666 666")
            .build(); 

        comprador = Comprador.builder()
            .nombre("Comprador")
            .apellidos("Test Test")
            .fechaNacimiento(LocalDate.of(1999, Month.APRIL, 9))
            .telefono("666 666 666")
            .correo("comprador@gmail.com")
            .imagenComprador(null)
            .administrador(administrador)
            .build(); 
    }

    @Test
    @DisplayName("Test de servicio para persistir un comprador")
    public void testGuardarComprador() {

        //given 
        given(compradorDao.save(comprador)).willReturn(comprador); 

        //when
        Comprador compradorGuardado = compradorService.save(comprador); 

        //then 
        assertThat(compradorGuardado).isNotNull(); 
    }

    @Test
    @DisplayName("Recupera una lista vacía de compradores")
    public void testListaVaciaCompradores() {

        //given 
        given(compradorDao.findAll()).willReturn(Collections.emptyList()); 

        //when 
        List<Comprador> compradores = compradorDao.findAll();

        //then 
        assertThat(compradores).isEmpty();
    }

    
}
