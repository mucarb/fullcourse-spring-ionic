package com.murilorb.coursespringionic.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.murilorb.coursespringionic.security.UserSS;

public class UserService {

	// retornando o usuario autenticado
	public static UserSS authenticated() {
		try {
			// retorna o usuario que estiver logado no sistema
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
