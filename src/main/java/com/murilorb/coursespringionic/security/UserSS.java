package com.murilorb.coursespringionic.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.murilorb.coursespringionic.domains.enums.Profile;

// Classe que exerce contrato com Spring Security para trabalhar com usuarios
public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {
	}

	public UserSS(Integer id, String email, String password, Set<Profile> profiles) {
		this.id = id;
		this.email = email;
		this.password = password;
		// convertendo conjunto de perfis pra GrantedAuthority,
		// recebendo a descricao para o padrao do Spring Security
		this.authorities = profiles.stream().map(x -> new SimpleGrantedAuthority(x.getDescription()))
				.collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
