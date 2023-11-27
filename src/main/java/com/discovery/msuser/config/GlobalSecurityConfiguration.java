package com.discovery.msuser.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class GlobalSecurityConfiguration {

    private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;

    public GlobalSecurityConfiguration(TokenConverterProperties properties) {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter
                = new JwtGrantedAuthoritiesConverter();
        this.keycloakJwtTokenConverter
                = new KeycloakJwtTokenConverter(
                jwtGrantedAuthoritiesConverter,
                properties);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) -> {
            authorizeHttpRequests
                    .requestMatchers("/api/v1/users/students").permitAll()
                    .requestMatchers("/api/v1/users/professors").permitAll()

                    .requestMatchers("/api/v1/users/professors/**").hasRole("CREATE-COURSES")

                    .requestMatchers("/api/v1/users/student/{userId}/subscription").hasRole("VIEW-COURSES")
                    .requestMatchers("/**").permitAll();
            //.anyRequest().permitAll();
        });
        http.oauth2ResourceServer((oauth2) -> {
            oauth2.jwt((jwt) -> jwt.jwtAuthenticationConverter(keycloakJwtTokenConverter));
        });
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(withDefaults());
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}

