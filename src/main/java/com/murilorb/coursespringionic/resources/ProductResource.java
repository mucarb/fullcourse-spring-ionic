package com.murilorb.coursespringionic.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.murilorb.coursespringionic.domains.Product;
import com.murilorb.coursespringionic.domains.dtos.ProductDTO;
import com.murilorb.coursespringionic.domains.dtos.ProductNewDTO;
import com.murilorb.coursespringionic.resources.utils.URL;
import com.murilorb.coursespringionic.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Integer id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ProductNewDTO objNewDto) {
		Product obj = service.fromDTO(objNewDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findPage(@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categories", defaultValue = "0") String categories,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		String nameDecoded = URL.decodeParam(name);
		List<Integer> ids = URL.decodeIntList(categories);
		Page<Product> pagination = service.search(nameDecoded, ids, page, linesPerPage, direction, orderBy);
		Page<ProductDTO> paginationDto = pagination.map((obj) -> new ProductDTO(obj));
		return ResponseEntity.ok().body(paginationDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping(value = "/{id}/picture")
	public ResponseEntity<Void> uploadPicture(@RequestParam(name = "file") MultipartFile file,
			@PathVariable Integer id) {
		URI uri = service.uploadPicture(file, id);
		return ResponseEntity.created(uri).build();
	}

}
