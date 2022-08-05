package com.murilorb.coursespringionic.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.murilorb.coursespringionic.domains.Customer;
import com.murilorb.coursespringionic.domains.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

	@Transactional(readOnly = true)
	Page<Purchase> findByCustomer(Customer customer, Pageable pageRequest);

}
