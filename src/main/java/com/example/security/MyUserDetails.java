package com.example.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.user.Role;
import com.example.user.User;

import lombok.Data;

/**
 * Esta clase es una implementación de UserDetails, que es una interfaz
 * proporcionada por Spring Security para representar a un usuario autenticado
 * en el sistema. Esta clase tiene los campos necesarios para representar los
 * detalles de un usuario, como su nombre de usuario y contraseña, así como las
 * autorizaciones de seguridad que tiene.
 * También tiene métodos que se utilizan para verificar si una cuenta de usuario
 * está bloqueada, caducada o habilitada, que son comprobaciones de seguridad
 * adicionales que se pueden hacer en un sistema. En resumen, esta clase se
 * utiliza para proporcionar los detalles de un usuario autenticado en un
 * sistema y verificar su seguridad.
 */

@Data
public class MyUserDetails implements UserDetails {

    // Esta clase tiene tres campos:
    private String userName;
    private String password;

    private List<GrantedAuthority> authorities; // almacena la lista de autorizaciones de seguridad del usuario.

    // MÉTODOS:
    /**
     * El constructor de la clase MyUserDetails toma un objeto User como parámetro.
     * El objeto User contiene los detalles del usuario, como su nombre, contraseña
     * y autorizaciones de seguridad.
     */
    public MyUserDetails(User user) {

        // asigna el nombre de usuario y la contraseña del objeto User a los campos
        // userName y password de la clase MyUserDetails, respectivamente.
        this.userName = user.getEmail();
        this.password = user.getPassword();

        // A continuación, se crea una lista de autorizaciones de seguridad para el
        // usuario. Para hacer esto, se convierten los valores del enum Role en una
        // matriz de strings usando el método toString() y luego se dividen en una lista
        // de strings utilizando el método split(). Luego se utiliza la operación map()
        // para crear una nueva lista de autorizaciones de seguridad, que se almacena en
        // el campo authorities;
        authorities = Arrays
                .stream(Role.values().toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Los métodos más importantes son getAuthorities(), getPassword() y
     * getUsername(), que devuelven la lista de autorizaciones de seguridad del
     * usuario, la contraseña del usuario y el nombre de usuario del usuario,
     * respectivamente.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    /**
     * Los métodos isAccountNonExpired(), isAccountNonLocked() y
     * isCredentialsNonExpired() devuelven todos "true", lo que significa que la
     * cuenta del usuario no ha caducado, no está bloqueada y las credenciales del
     * usuario no han caducado, respectivamente.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * El método isEnabled() devuelve "true", lo que significa que la cuenta del
     * usuario está habilitada.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
