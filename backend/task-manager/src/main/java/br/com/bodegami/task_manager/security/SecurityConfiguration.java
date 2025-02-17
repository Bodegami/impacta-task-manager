package br.com.bodegami.task_manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String ADMINISTRATOR = "ADMINISTRATOR";
    private static final String CUSTOMER = "CUSTOMER";

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/login", // Url que usaremos para fazer login
            "/users" // Url que usaremos para criar um usuário
    };

    // Endpoints que requerem autenticação para serem acessados
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/users",
            "/users/*",
            "/users/test" // Endpoint que usaremos para testar se o usuário está autenticado
    };

    // Endpoints que só podem ser acessador por usuários com permissão de cliente
    public static final String [] ENDPOINTS_CUSTOMER = {
            "/users",
            "/users/*",
            "/users/test/customer"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de administrador
    public static final String [] ENDPOINTS_ADMIN = {
            "/users",
            "/users/",
            "/users/*",
            "/users/test/administrator"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Desativa a proteção contra CSRF
                .sessionManagement(AbstractHttpConfigurer::disable) // Desativa o controle de sessão
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/users", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/*").hasAnyRole(ADMINISTRATOR, CUSTOMER)
                        .requestMatchers(HttpMethod.PUT, "/users/*").hasAnyRole(ADMINISTRATOR, CUSTOMER)
                        .requestMatchers(HttpMethod.DELETE, "/users/*").hasRole(ADMINISTRATOR)
                        .requestMatchers(HttpMethod.GET, "/users/test/customer").hasRole(CUSTOMER)
                        .requestMatchers(HttpMethod.DELETE, "/users/test/administrator").hasRole(ADMINISTRATOR)
                    .anyRequest().denyAll())
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
