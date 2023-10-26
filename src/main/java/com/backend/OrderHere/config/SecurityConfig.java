package com.backend.OrderHere.config;


import com.backend.OrderHere.auth.ApplicationUserService;
import com.backend.OrderHere.filter.JwtCredentialsAuthenticationFilter;
import com.backend.OrderHere.filter.JwtVerifyFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

  private final ApplicationUserService applicationUserService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .sessionManagement( (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilter(new JwtCredentialsAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
            .addFilterAfter(new JwtVerifyFilter(), JwtCredentialsAuthenticationFilter.class)
            .authorizeHttpRequests(auth ->
                    auth.requestMatchers(StaticConfig.ignoreUrl).permitAll()
                            .anyRequest().authenticated());
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer(AuthenticationManagerBuilder auth){
    return web -> auth.authenticationProvider(daoAuthenticationProvider());
  }
  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService((applicationUserService));
    return provider;
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

