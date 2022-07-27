package com.murilorb.coursespringionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilorb.coursespringionic.domains.PurchaseItem;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Integer> {

}
