package com.store.estore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.store.estore.security.JwtEntryPoint;
import com.store.estore.security.JwtFilter;
import com.store.estore.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true, securedEnabled = true, jsr250Enabled = true)
public class EStoreSecurityConfig {

  @Autowired
  public JwtEntryPoint jwtEntryPoint;

  @Autowired
  public UserDetailsServiceImpl userDetailsServiceImpl;

  @Autowired
  public JwtFilter jwtFilter;

  @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
  }
  
  @Bean 
  public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		String hierarchy = "ROLE_ADMIN > ROLE_STAFF";
		hierarchy += " \n ";
		hierarchy += "ROLE_STAFF > ROLE_USER";
		roleHierarchyImpl.setHierarchy(hierarchy);
		
		return roleHierarchyImpl;
	}

	@Bean
	public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(this.roleHierarchy());

		return expressionHandler;
	}

	@Bean
	public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf
    .disable())
    .exceptionHandling(exp -> exp.authenticationEntryPoint(jwtEntryPoint))
    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(auth -> auth.requestMatchers("/article/**").permitAll()
      .requestMatchers("/h2-console/**").permitAll()
      .requestMatchers("/auth/**").permitAll()
      .requestMatchers("/**").permitAll()
      .anyRequest()
      .authenticated())
    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    
    //TODO: for debug with h2 (/** and /h2-console/**)
    http.headers((headers) -> headers.frameOptions().sameOrigin());

    return http.build();
	}

  /*@Bean
  CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();

      configuration.setAllowedOrigins(List.of("http://localhost:4200"));
      configuration.setAllowedMethods(List.of("GET","POST"));
      configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

      source.registerCorsConfiguration("/**",configuration);

      return source;
  }*/

}
