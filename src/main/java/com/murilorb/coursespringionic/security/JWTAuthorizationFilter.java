package com.murilorb.coursespringionic.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// filtro de autorizacao
// que ira verificar se o token é valido extraindo o usuario do proprio
// e buscar no banco de dados pra ver se existe
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;

	// usado pra fazer a busca de usuario pelo email
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	// executa antes de deixar a requisicao continuar
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// pegar o cabecao da requisicao para extrair o token
		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			// instanciando objeto de autenticacao apartir do token
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));

			if (auth != null) {
				// funcao para liberar o acesso pelo filtro
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		// depois dos testes, sera executado a requisicao normalmente
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		// se o token e valido entao esta autorizado
		if (jwtUtil.validToken(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}

}
