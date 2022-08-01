package com.murilorb.coursespringionic.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.murilorb.coursespringionic.domains.Category;
import com.murilorb.coursespringionic.domains.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	// busca de produtos por nome e categorias
	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cat WHERE obj.name LIKE %:name% AND cat IN :categories")
	Page<Product> search(@Param("name") String name, @Param("categories") List<Category> categories,
			Pageable pageRequest);

}
