package com.example.busticket.config;

import com.example.busticket.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  private final CustomUserDetailsService userDetailsService;

  public SecurityConfig(CustomUserDetailsService uds){ this.userDetailsService = uds; }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth.setUserDetailsService(userDetailsService);
    auth.setPasswordEncoder(passwordEncoder());
    return auth;
  }

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
