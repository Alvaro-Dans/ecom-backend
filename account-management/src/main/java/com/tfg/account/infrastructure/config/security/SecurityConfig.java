package com.tfg.account.infrastructure.config.security;

import com.tfg.account.domain.user.User;
import com.tfg.account.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository repo) {
        return username -> {
            User u = repo.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(u.getEmail())
                    .password(u.getPasswordHash())
                    .roles(u.getRoles().stream().map(r -> r.getName()).toArray(String[]::new))
                    .build();
        };
    }

    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService uds, PasswordEncoder enc) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(enc);
        return p;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    /*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("https://localhost:4200"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                // desactiva CSRF si no lo necesitas (para API REST)
                .csrf(AbstractHttpConfigurer::disable)
                // reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // 1) siempre permitir preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 2) endpoints públicos
                        .requestMatchers("/api/v1/account/register").permitAll()
                        .requestMatchers("/api/v1/account/login", "/api/v1/auth/logout").permitAll()
                        // 3) swagger, si lo usas
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        // 4) resto de GETs públicos (p.ej. auth-status)
                        .requestMatchers(HttpMethod.GET, "/api/v1/account/auth-status").permitAll()
                        // 5) el GET user-info requiere sesión: quítalo de permitAll
                        .requestMatchers(HttpMethod.GET, "/api/v1/account/user-info").permitAll()
                        // 6) todo lo demás exige autenticación
                        .anyRequest().authenticated()
                )
                // sesiones por cookie
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                // deshabilita form login / basic
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                // configura logout
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
