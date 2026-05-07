package com.example.busticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/css/**", "/register", "/login", "/search", "/public/**").permitAll()
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/staff/**").hasAnyRole("STAFF","ADMIN")
        .anyRequest().authenticated()
      )
      .formLogin(form -> form.loginPage("/login").permitAll())
      .logout(logout -> logout.logoutUrl("/logout").permitAll())
      .csrf().and();
    return http.build();
  }
}
