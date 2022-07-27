package com.murilorb.coursespringionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilorb.coursespringionic.domains.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
