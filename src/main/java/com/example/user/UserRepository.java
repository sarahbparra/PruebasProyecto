package com.example.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Añadimos estos métodos a los de JpaRepository:
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
/**
 * En este caso no sería necesaria la capa Service porque con el Dao es
 * suficiente ya que solo queremos comprobar si un usuario existe o no
 */
