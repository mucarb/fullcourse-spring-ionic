package com.murilorb.coursespringionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilorb.coursespringionic.domains.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
