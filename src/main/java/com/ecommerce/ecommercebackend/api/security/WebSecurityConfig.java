package com.ecommerce.ecommercebackend.api.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/product","auth/register","/auth/me","/auth/login","/auth/verify").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }
}
