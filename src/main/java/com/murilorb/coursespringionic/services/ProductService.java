package com.murilorb.coursespringionic.services;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.murilorb.coursespringionic.domains.Category;
import com.murilorb.coursespringionic.domains.Product;
import com.murilorb.coursespringionic.domains.dtos.ProductNewDTO;
import com.murilorb.coursespringionic.repositories.CategoryRepository;
import com.murilorb.coursespringionic.repositories.ProductRepository;
import com.murilorb.coursespringionic.services.exception.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private DropboxService dropboxService;

	@Value("${img.prefix.product}")
	private String prefix;

	@Value("${img.size}")
	private Integer size;

	public Product findById(Integer id) {
		Optional<Product> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName()));
	}

	public Product insert(Product obj) {
		return repository.save(obj);
	}

	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String direction,
			String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categories = categoryRepository.findAllById(ids);
		return repository.search(name, categories, pageRequest);
	}

	public URI uploadPicture(MultipartFile file, Integer id) {
		BufferedImage jpgImage = imageService.getJpgImageFromFile(file);

		// image adjustments
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + id + ".jpg";
		InputStream is = imageService.getInputStream(jpgImage, "jpg");
		URI uri = dropboxService.uploadFile(is, fileName);

		Optional<Product> prod = repository.findById(id);
		prod.get().setImageUrl(uri.toString());
		repository.save(prod.get());
		return uri;
	}

	public Product fromDTO(ProductNewDTO objNewDto) {
		Product obj = new Product(null, objNewDto.getName(), objNewDto.getPrice());

		for (Integer categoryId : objNewDto.getCategoriesId()) {
			Category category = categoryService.findById(categoryId);

			if (category != null) {
				category.getProducts().add(obj);
				obj.getCategories().add(category);
			}
		}
		return obj;
	}

}
