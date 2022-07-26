package com.murilorb.coursespringionic.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@GetMapping
	public String listing() {
		return "REST está funcionando";
	}

}
