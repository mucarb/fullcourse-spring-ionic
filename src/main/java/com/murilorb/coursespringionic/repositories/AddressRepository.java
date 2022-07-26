package com.murilorb.coursespringionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilorb.coursespringionic.domains.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
