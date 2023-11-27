package com.discovery.msuser.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class GlobalSecurityConfiguration {
    @Autowired
    private SecurityProperties securityProperties;
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
        http.authorizeHttpRequests(authz -> {
            for (SecurityProperties.Route route : securityProperties.getRoutes()) {
                HttpMethod httpMethod = getHttpMethod(route.getMethod());
                if (httpMethod != null) {
                    if (route.getRoles() == null || route.getRoles().isEmpty()) {
                        authz.requestMatchers(httpMethod, route.getPath()).permitAll();
                    } else {
                        authz.requestMatchers(httpMethod, route.getPath())
                                .hasAnyRole(route.getRoles().toArray(new String[0]));
                    }

                }
            }
            authz.anyRequest().permitAll();
        });

        http.oauth2ResourceServer((oauth2) -> {
            oauth2.jwt((jwt) -> jwt.jwtAuthenticationConverter(keycloakJwtTokenConverter));
        });
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(withDefaults());
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
    private HttpMethod getHttpMethod(String method) {
        if (method == null) return null;
        switch (method.toUpperCase()) {
            case "GET":
                return HttpMethod.GET;
            case "POST":
                return HttpMethod.POST;
            case "PUT":
                return HttpMethod.PUT;
            case "DELETE":
                return HttpMethod.DELETE;
            case "PATCH":
                return HttpMethod.PATCH;
            // Agrega más casos si es necesario
            default:
                return null;
        }
    }
}

