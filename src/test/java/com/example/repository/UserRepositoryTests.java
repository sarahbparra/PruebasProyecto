package com.example.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.user.Role;
import com.example.user.User;
import com.example.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class UserRepositoryTests {
    
    @Autowired
    private UserRepository userRepository; 

    private User comprador0; 

    @BeforeEach
    void setUp() {
        comprador0 = User.builder()
                .userName("Comprador0")
                .password("123456")
                .email("user0@gmail.com")
                .role(Role.COMPRADOR)
                .build();
    }

    @Test
    @DisplayName("Test para agregar un comprador")
    public void testAddComprador() {
        /**
         * Segun el enfoque: Una prueba unitaria se divide en tres partes
         *
         * 1. Arrange: Setting up the data that is required for this test case
         * 2. Act: Calling a method or Unit that is being tested.
         * 3. Assert: Verify that the expected result is right or wrong.
         *
         * Segun el enfoque BDD
         *
         * 1. given
         * 2. when
         * 3. then
         */

        // given - dado que:

        User comprador1 = User.builder()
        .userName("Test Comprador 1")
        .password("123456")
        .email("comprador1@gmail.com")
        .role(Role.COMPRADOR)
        .build();

        // when 
        User userAdded = userRepository.save(comprador1); 

        // then 
        assertThat(userAdded).isNotNull();
        assertThat(userAdded.getId()).isGreaterThan(0L);
    }

    @DisplayName("Test para listar compradores")
    @Test
    public void testFindAllCompradores(){

        // given 
        User comprador2 = User.builder()
        .userName("Test Comprador 2")
        .password("123456")
        .email("comprador2@gmail.com")
        .role(Role.COMPRADOR)
        .build();
        
        userRepository.save(comprador0); 
        userRepository.save(comprador2); 

        // when 
        List<User> compradores = userRepository.findAll(); 

        // then
        assertThat(compradores).isNotNull(); 
        assertThat(compradores.size()).isEqualTo(2); 

    }

    @Test
    @DisplayName("Test para recuperar un comprador por su ID")
    public void findCompradorById() {

        // given

        userRepository.save(comprador0);

        // when

        User comprador = userRepository.findById(comprador0.getId()).get();

        // then

        assertThat(comprador.getId()).isNotEqualTo(0L);

    }

    @Test
    @DisplayName("Test para actualizar un comprador")
    public void testUpdateComprador() {

        // given

        userRepository.save(comprador0);

        // when

        User compradorGuardado = userRepository.findByEmail(comprador0.getEmail()).get();

        compradorGuardado.setUserName("Prueba");
        compradorGuardado.setEmail("prueba@gg.com");

        User compradorUpdated = userRepository.save(compradorGuardado);

        // then

        assertThat(compradorUpdated.getEmail()).isEqualTo("prueba@gg.com");

    }

    @DisplayName("Test para eliminar un comprador")
    @Test
    public void testDeleteComprador() {

        // given
        userRepository.save(comprador0);

        // when
        userRepository.delete(comprador0);
        Optional<User> optionalComprador = userRepository.findByEmail(comprador0.getEmail());

        // then
        assertThat(optionalComprador).isEmpty();
    }

}
