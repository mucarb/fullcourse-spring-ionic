package com.murilorb.coursespringionic.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Purchase obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public ResponseEntity<Page<Purchase>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "instant") String orderBy) {
		Page<Purchase> pagination = service.findPage(page, linesPerPage, direction, orderBy);
		return ResponseEntity.ok().body(pagination);
	}

}
