package com.techlabs.capstone.config;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.techlabs.capstone.security.JwtAuthenticationEntryPoint;
import com.techlabs.capstone.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter authenticationFilter;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(withDefaults());
		http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
		
		http.authorizeHttpRequests(request -> request.requestMatchers("/e-commerce/register").permitAll()
				.requestMatchers("/e-commerce/login").permitAll()
				.requestMatchers(HttpMethod.GET, "/e-commerce/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/e-commerce/**").authenticated()
				.requestMatchers(HttpMethod.PUT, "/e-commerce/**")
				.authenticated().requestMatchers(HttpMethod.DELETE, "/e-commerce/**").authenticated().anyRequest()
				.authenticated());

		http.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}