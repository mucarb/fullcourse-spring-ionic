package com.murilorb.coursespringionic.services;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.murilorb.coursespringionic.domains.Category;
import com.murilorb.coursespringionic.domains.dtos.CategoryDTO;
import com.murilorb.coursespringionic.repositories.CategoryRepository;
import com.murilorb.coursespringionic.services.exception.DataIntegrityException;
import com.murilorb.coursespringionic.services.exception.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private DropboxService dropboxService;

	@Value("${img.prefix.category}")
	private String prefix;

	@Value("${img.size}")
	private Integer size;

	public List<Category> findAll() {
		return repository.findAll();
	}

	public Category findById(Integer id) {
		Optional<Category> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}

	public Category insert(Category obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Category update(Category obj) {
		Category entity = findById(obj.getId());
		updateData(entity, obj);
		return repository.save(entity);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possua produto(s)!");
		}
	}

	// Retorna uma pagina de dados
	public Page<Category> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		// nº da pagina, qtd de linhas, ordem(ascendente ou descrecente), ordenação
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Category fromDTO(CategoryDTO objDto) {
		Category obj = new Category(objDto.getId(), objDto.getName());
		return obj;
	}

	private void updateData(Category entity, Category obj) {
		entity.setName(obj.getName());
	}

	public URI uploadPicture(MultipartFile file, Integer id) {
		BufferedImage jpgImage = imageService.getJpgImageFromFile(file);

		// image adjustments
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + id + ".jpg";
		InputStream is = imageService.getInputStream(jpgImage, "jpg");
		URI uri = dropboxService.uploadFile(is, fileName);

		Optional<Category> cat = repository.findById(id);
		cat.get().setImageUrl(uri.toString());
		repository.save(cat.get());

		return uri;
	}

}
