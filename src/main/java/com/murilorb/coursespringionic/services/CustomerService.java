package com.murilorb.coursespringionic.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.murilorb.coursespringionic.domains.Address;
import com.murilorb.coursespringionic.domains.City;
import com.murilorb.coursespringionic.domains.Customer;
import com.murilorb.coursespringionic.domains.dtos.CustomerDTO;
import com.murilorb.coursespringionic.domains.dtos.CustomerNewDTO;
import com.murilorb.coursespringionic.domains.enums.CustomerType;
import com.murilorb.coursespringionic.domains.enums.Profile;
import com.murilorb.coursespringionic.repositories.AddressRepository;
import com.murilorb.coursespringionic.repositories.CustomerRepository;
import com.murilorb.coursespringionic.security.UserSS;
import com.murilorb.coursespringionic.services.exception.AuthorizationException;
import com.murilorb.coursespringionic.services.exception.DataIntegrityException;
import com.murilorb.coursespringionic.services.exception.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DropboxService dropboxService;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.size}")
	private Integer size;

	public List<Customer> findAll() {
		return repository.findAll();
	}

	public Customer findById(Integer id) {
		UserSS user = UserService.authenticated();

		// testando o perfil
		// a busca e exercida somente por admin ou com id solicitado é o mesmo do
		// usuario
		if (user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		Optional<Customer> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Customer.class.getName()));
	}

	public Customer findByEmail(String email) {
		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		Customer obj = repository.findByEmail(email);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Email: " + email + ", Tipo: " + Customer.class.getName());
		}
		return obj;
	}

	@Transactional
	public Customer insert(Customer obj) {
		obj.setId(null);
		obj = repository.save(obj);
		addressRepository.saveAll(obj.getAdresses());
		return obj;
	}

	public Customer update(Customer obj) {
		Customer entity = findById(obj.getId());
		updateData(entity, obj);
		return repository.save(entity);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o cliente porque há pedido(s) relacionados!");
		}
	}

	public Page<Customer> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Customer fromDTO(CustomerDTO objDto) {
		return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
	}

	public Customer fromDTO(CustomerNewDTO objDto) {
		Customer customer = new Customer(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOrCnpj(),
				CustomerType.toEnum(objDto.getType()), passwordEncoder.encode(objDto.getPassword()));
		City city = new City(objDto.getCityId(), null, null);
		Address address = new Address(null, objDto.getPublicPlace(), objDto.getNumber(), objDto.getComplement(),
				objDto.getNeighborhood(), objDto.getZipCode(), city, customer);

		customer.getAdresses().add(address);
		customer.getPhones().add(objDto.getPhone1());

		if (objDto.getPhone2() != null) {
			customer.getPhones().add(objDto.getPhone2());
		}
		if (objDto.getPhone3() != null) {
			customer.getPhones().add(objDto.getPhone3());
		}
		return customer;
	}

	private void updateData(Customer entity, Customer obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile file) {
		UserSS user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		BufferedImage jpgImage = imageService.getJpgImageFromFile(file);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		String fileName = prefix + user.getId() + ".jpg";
		return dropboxService.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName);
	}

}
