package com.library.authservice.security;


import com.library.authservice.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true,securedEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private static final String ROLE_EMPLOYEE = "EMPLOYEE";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_CUSTUMER = "CUSTOMER";
    private static final List<String> ROLES_CUSTUMER_EMPLOYEE_ADMIN = List.of(ROLE_CUSTUMER,ROLE_ADMIN,ROLE_EMPLOYEE);
    private static final List<String> ROLES_CUSTUMER_EMPLOYEE = List.of(ROLE_CUSTUMER,ROLE_EMPLOYEE);
    private static final List<String> ROLES_CUSTUMER_ONLY = List.of(ROLE_CUSTUMER);
    private static final List<String> ROLES_EMPLOYEE_ADMIN = List.of(ROLE_ADMIN,ROLE_EMPLOYEE);
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/***")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
