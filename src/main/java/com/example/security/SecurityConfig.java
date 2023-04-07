
package com.example.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] SECURED_URLs = { "/productos/**" }; // solo aaaalgunos usuarios puededn hacer cosas
                                                                      // con los endpoints que empiezan asi, el tipo de
                                                                      // usuario queda especificado más abajo

    private static final String[] UN_SECURED_URLs = { "/users/**" }; // cualquiera puede modificar con esta url

    @Bean // anotación exclusiva para métodos. Siempre retorna un objeto (lo crea e
    // inyecta)
    //si pones el main necesitas el static
    static PasswordEncoder passwordEncoder() {
        // PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // //Este main lo ponemos aquí y no en app para que no sea necesario levantar
    // absolutamente todo ell proyecto cada vez que generemos una constraseña
    // public static void main(String[] args) {
    //     System.out.println(new SecurityConfig().passwordEncoder().encode("123456"));
    // }

//CONTRASEÑA GENERADA POR ESTE MÉTODO: $2a$10$nARlnlVgqDZoY7taR0MCoe6eE4zL5v/CK.qprkP5IJ4Rj8yxTctVq

    // Métodos creados:

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers(UN_SECURED_URLs).permitAll().and()
                .authorizeHttpRequests().requestMatchers(SECURED_URLs)
                .hasAuthority("ADMIN").anyRequest()
                .authenticated().and().httpBasic(withDefaults());

        return http.build();

    }

}
