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
            "/users", // Url que usaremos para criar um usuário
            "/tasks",
            "/tasks/*"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Desativa a proteção contra CSRF
                .sessionManagement(AbstractHttpConfigurer::disable) // Desativa o controle de sessão
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/users", "/login", "/tasks", "/tasks/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/*", "/tasks/*", "/tasks/**").hasAnyRole(ADMINISTRATOR, CUSTOMER)
                        .requestMatchers(HttpMethod.PUT, "/users/*", "/tasks/*", "/tasks/**").hasAnyRole(ADMINISTRATOR, CUSTOMER)
                        .requestMatchers(HttpMethod.DELETE, "/users/*", "/tasks/**").hasRole(ADMINISTRATOR)
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
