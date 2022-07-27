package com.murilorb.coursespringionic.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murilorb.coursespringionic.domains.Purchase;
import com.murilorb.coursespringionic.services.PurchaseService;

@RestController
@RequestMapping(value = "/purchases")
public class PurchaseResource {

	@Autowired
	private PurchaseService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Purchase> findById(@PathVariable Integer id) {
		Purchase obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
