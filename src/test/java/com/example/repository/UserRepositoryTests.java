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

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class UserRepositoryTests {
    
    @Autowired
    private UserRepository userRepository; 

    private User user0; 

    @BeforeEach
    void setUp() {
        user0 = User.builder()
                .userName("Comprador0")
                .password("123456")
                .email("user0@gmail.com")
                .role(Role.COMPRADOR)
                .build();
    }

    @Test
    @DisplayName("Test para agregar un user")
    public void testAddUser() {
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

        User user = User.builder()
        .userName("Test Comprador 1")
        .password("123456")
        .email("comprador1@gmail.com")
        .role(Role.COMPRADOR)
        .build();

        // when 
        User userAdded = userRepository.save(user); 

        // then 
        assertThat(userAdded).isNotNull();
        assertThat(userAdded.getId()).isGreaterThan(0L);
    }

    
}
