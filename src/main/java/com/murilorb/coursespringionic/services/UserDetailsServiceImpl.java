package com.murilorb.coursespringionic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.murilorb.coursespringionic.domains.Customer;
import com.murilorb.coursespringionic.repositories.CustomerRepository;
import com.murilorb.coursespringionic.security.UserSS;

// Classe com contrato do Spring Security que permite a busca pelo nome do usuario
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository repository;

	// implementando busca por email do usuario
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer cust = repository.findByEmail(email);

		if (cust == null) {
			// excecao do Spring Secutity
			throw new UsernameNotFoundException(email);
		}
		// intanciando UserSS a partir de um cliente(customer)
		return new UserSS(cust.getId(), cust.getEmail(), cust.getPassword(), cust.getProfiles());
	}

}
