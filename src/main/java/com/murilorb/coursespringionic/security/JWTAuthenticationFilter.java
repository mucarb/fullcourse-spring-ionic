package com.murilorb.coursespringionic.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murilorb.coursespringionic.domains.dtos.CredentialsDTO;

/* classe que ao extender UsernamePasswordAuthenticationFilter autometicamente o Spring Security ira interceptar a requisicao de login */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			// instanciando credencias a partir da requisicao do login
			CredentialsDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);
			// instanciando token de autenticacao pelo email e senha das credenciais
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),
					creds.getPassword(), new ArrayList<>());
			/*
			 * instanciando um Authentication pela validacao(authenticate) o framework
			 * conhece os contratos implementados pelo UserDetails UserUserDetailsService e
			 * verifica se usuario e senha sao validos este objeto informe para Spring
			 * Security se a autenticacao ocorreu com sucesso ou nao
			 */
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		/*
		 * getPrincipal retorna um usuario do Spring Security, aplicando casting para
		 * UserSS que implementa UserDeatails
		 */
		String username = ((UserSS) auth.getPrincipal()).getUsername();
		// gerando um token a partir do email
		String token = jwtUtil.generateToken(username);
		// retorna o token na resposta da requisicao, adicionando o no cabecalho
		response.addHeader("Authorization", "Bearer " + token);
	}

	// gerando status code 401 correto para spring 2.x.x para credenciais erradas
	/*
	 * Fonte: https://github.com/acenelio/springboot2-ionic-backend/blob/
	 * beed651977f7dc0c4cd2b19196622f4d595c003a/src/main/java/com/nelioalves/cursomc
	 * /security/JWTAuthenticationFilter.java
	 */
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			// a resposta retorna status code 401
			response.setStatus(401);
			// em um corpo json
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			long date = new Date().getTime();
			// conteudo
			return "{\"timestamp\": " + date
					+ ", \"status\": 401, \"error\": \"Não autorizado\", \"message\": \"Email ou senha inválidos\", \"path\": \"/login\"}";
		}

	}

}