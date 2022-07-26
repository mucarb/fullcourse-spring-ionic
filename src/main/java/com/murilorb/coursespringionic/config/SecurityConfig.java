package com.murilorb.coursespringionic.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.murilorb.coursespringionic.security.JWTAuthenticationFilter;
import com.murilorb.coursespringionic.security.JWTAuthorizationFilter;
import com.murilorb.coursespringionic.security.JWTUtil;

// Classe para configuracoes de seguranca
@Configuration
@EnableWebSecurity
//ira permitir anotacaoes de pre autorizacao nos endpoints
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private Environment env;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthenticationConfiguration auth;

	@Autowired
	private UserDetailsService userDetailsService;

	// caminhos que estao liberados sem autenticacao
	public static final String[] PUBLIC_MATCHERS = { "/h2-console/**", "/error" };

	// caminhos que estao liberados sem autenticacao, acesso somente a leitura
	public static final String[] PUBLIC_MATCHERS_GET = { "/products/**", "/categories/**", "/states/**" };

	public static final String[] PUBLIC_MATCHERS_POST = { "/customers/**", "/auth/forgot/**" };

	@Bean
	public SecurityFilterChain basicUrlAuthenticationSettings(HttpSecurity http) throws Exception {
		// pegando perfils ativos do projeto
		// se caso o perfil ativo seja de teste (test), significa que quero acesso ao H2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			// camando pra liberar o acesso
			http.headers().frameOptions().disable();
		}
		// desabilitando proteção a CSRF no backend
		http.cors().and().csrf().disable();
		// liberando autorizacao e exigindo autenticacao dos caminhos restantes
		// PUBLIC_MATCHERS_GET caminhos somente para leitura, ou seja, requisição GET
		http.authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().antMatchers(PUBLIC_MATCHERS).permitAll()
				.anyRequest().authenticated();
		// adicionando filtro de autenticacao e geracao do token n arequisicao
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(auth), jwtUtil));
		// adicionando filtro de autorizacao
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(auth), jwtUtil, userDetailsService));
		// garantindo que seja criado sessao de usuario
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
	}

	/*
	 * Fonte:
	 * https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-
	 * deprecated-websecurityconfigureradapter-in-spring
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// permitindo acesso aos endpoint por multiplas fontes com as config. basicas
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// classe para injetar um BCryptPasswordEncoder em qualquer classe do sistema
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
