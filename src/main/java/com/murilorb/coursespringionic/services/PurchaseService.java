package com.murilorb.coursespringionic.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilorb.coursespringionic.domains.Purchase;
import com.murilorb.coursespringionic.repositories.PurchaseRepository;
import com.murilorb.coursespringionic.services.exception.ObjectNotFoundException;

@Service
public class PurchaseService {

	@Autowired
	private PurchaseRepository repository;

	public Purchase findById(Integer id) {
		Optional<Purchase> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Purchase.class.getName()));
	}

}
