package com.murilorb.coursespringionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilorb.coursespringionic.domains.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

}
