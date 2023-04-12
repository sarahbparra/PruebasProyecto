
package com.example.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * La clase "SecurityConfig" es una configuración de seguridad para la
 * aplicación web.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Se definen dos arreglos de strings llamados SECURED_URLs y UN_SECURED_URLs.
     * Estos arreglos contienen los endpoints que se deben proteger y las que no se
     * deben proteger, respectivamente.
     */
    private static final String[] SECURED_URLs = { "/productos/**" };
    // solo algunos usuarios puededn hacer cosas con los endpoints que empiezan asi,
    // el tipo de usuario queda especificado más abajo

    private static final String[] UN_SECURED_URLs = { "/users/**" };
    // cualquiera puede modificar con esta url

    /**
     * Se crea un bean que devuelve una instancia de BCryptPasswordEncoder, que es
     * un codificador de contraseñas para proteger las contraseñas de los usuarios
     * en la base de datos.
     */

    @Bean // anotación exclusiva para métodos. Siempre retorna un objeto (lo crea e
    // inyecta)
    // Si pones el main necesitas el static
    // static PasswordEncoder passwordEncoder() {
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * OJO: Método de un solo uso. Lo utilizamos para codificar la primera
     * contraseña que creemos y despues lo comentamos para que no interfiera
     */
    // //Este main lo ponemos aquí y no en app para que no sea necesario levantar
    // absolutamente todo ell proyecto cada vez que generemos una constraseña

    public static void main(String[] args) {
    System.out.println(new SecurityConfig().passwordEncoder().encode("1234"));
    }
    // CONTRASEÑA GENERADA POR ESTE MÉTODO:
    // $2a$10$z1gH0v8k6ve99mJ1zc3Gsea42SDst46vmmnoCkuwlqx.iO6vmtahy

    // Otros métodos creados:

    /**
     * Se define el método securityFilterChain que recibe como parámetro un objeto
     * HttpSecurity.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * Este método deshabilita la protección CSRF, que es una medida de seguridad
         * contra ataques de falsificación de solicitudes entre sitios.
         */
        http.csrf().disable();

        /**
         * Se utiliza el objeto http para configurar la protección de las URL en la
         * aplicación:
         * Por un lado, se permiten todas las solicitudes que coincidan con las URL en
         * UN_SECURED_URLs y se requiere autenticación para todas las solicitudes que
         * coincidan con las URL en SECURED_URLs.
         * Por otro, aara acceder a las URL en SECURED_URLs, se requiere la autoridad
         * "ADMIN". Si el usuario no tiene esta autoridad, se devuelve un error de
         * acceso denegado.
         */
        http.authorizeHttpRequests()
                .requestMatchers(UN_SECURED_URLs).permitAll().and()
                .authorizeHttpRequests().requestMatchers(SECURED_URLs)
                .hasAuthority("ADMIN").anyRequest()
                .authenticated().and().httpBasic(withDefaults());

        /**
         * Finalmente, se devuelve una cadena de filtro de seguridad que se usará en la
         * aplicación.
         */
        return http.build();

    }

    // OJO, COMPROBAR EN LOS CONTROLES QUÉ MÉTODOS TIENEN @SECURED

}
