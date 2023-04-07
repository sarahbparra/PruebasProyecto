package com.example.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.user.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * La clase MyUserDetailService es un servicio de Spring Security que implementa
 * la interfaz UserDetailsService y se encarga de cargar los detalles de un
 * usuario a través del método loadUserByUsername. Para ello, se inyecta una
 * instancia de UserRepository, que es una interfaz para acceder a la base de
 * datos de usuarios. Si se encuentra el usuario, se crea una instancia de
 * MyUserDetails (que implementa la interfaz UserDetails) y se devuelve. Si no
 * se encuentra el usuario, se lanza una excepción UsernameNotFoundException.
 */

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Esta interfaz define un solo método loadUserByUsername que debe ser
     * implementado para cargar los detalles del usuario por su nombre de usuario.
     */
    /**
     * Este método recibe como parámetro el nombre de usuario y se encarga de buscar
     * al usuario en la base de datos a través del método findByEmail del
     * UserRepository. Si se encuentra el usuario, se crea una instancia de
     * MyUserDetails (que implementa la interfaz UserDetails) y se devuelve.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(MyUserDetails::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Not user found"));
    }
    // Esto es una lambda que no recibe parametro, es unmétodo anónimo, por eso ()
    /**
     * Si no se encuentra el usuario, se lanza una excepción
     * UsernameNotFoundException. En este caso, se utiliza una lambda expression
     * para generar un mensaje de error personalizado que se adjunta a la excepción.
     */
}
