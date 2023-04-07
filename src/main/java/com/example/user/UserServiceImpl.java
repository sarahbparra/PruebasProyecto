package com.example.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Esta clase es una implementación de un servicio de usuario en un proyecto de
 * Spring Boot. Proporciona métodos para agregar, buscar, actualizar y eliminar
 * usuarios en un repositorio de usuarios. También encripta la contraseña de un
 * usuario antes de guardarla en el repositorio y maneja excepciones si un
 * usuario no se encuentra en el repositorio. En resumen, esta clase se encarga
 * de la lógica del negocio para manipular los datos de usuario en el proyecto.
 */

@Service // Esta anotación indica que esta clase es un servicio de Spring que debe ser
         // administrado por el contenedor de Spring
@RequiredArgsConstructor // Sirve para generar un constructor que inyecta las dependencias de
                         // UserRepository y PasswordEncoder
public class UserServiceImpl implements UserService {

    // Estos campos privados inyectados son objetos que se utilizan en los métodos
    // del servicio:
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Este método add se encarga de agregar un usuario al repositorio de usuarios.
     * Primero, busca si ya existe un usuario con el mismo correo electrónico en el
     * repositorio de usuarios utilizando el método findByEmail de userRepository.
     * Si el usuario ya existe, el método devuelve null. Si el usuario no existe, el
     * método utiliza el objeto PasswordEncoder para encriptar la contraseña del
     * usuario antes de guardarla en el repositorio utilizando el método save de
     * userRepository.
     */
    @Override
    public User add(User user) {
        Optional<User> theUser = userRepository.findByEmail(user.getEmail());

        if (theUser.isPresent()) {

            return null;

        }

        // Encriptamos la password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Este método findAll devuelve una lista de todos los usuarios del repositorio
     * utilizando el método findAll de userRepository.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Este método deleteByEmail se encarga de eliminar un usuario del repositorio
     * utilizando el método deleteByEmail de userRepository.
     */
    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    /**
     * Este método findByEmail busca un usuario en el repositorio utilizando el
     * método findByEmail de userRepository. Si el usuario no existe, el método
     * lanza una excepción UsernameNotFoundException.
     */
    @Override
    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario con el email: " + email));
    }

    /**
     * Este método update actualiza el rol de un usuario utilizando el método save
     * de userRepository.
     */
    @Override
    public User update(User user) {
        user.setRole(user.getRole());
        return userRepository.save(user);
    }

}