package com.murilorb.coursespringionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.murilorb.coursespringionic.domains.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Transactional(readOnly = true)
	Customer findByEmail(String email);

}
