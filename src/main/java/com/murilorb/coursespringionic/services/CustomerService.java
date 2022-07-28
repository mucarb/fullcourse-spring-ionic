package com.murilorb.coursespringionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.murilorb.coursespringionic.domains.Customer;
import com.murilorb.coursespringionic.domains.dtos.CustomerDTO;
import com.murilorb.coursespringionic.repositories.CustomerRepository;
import com.murilorb.coursespringionic.services.exception.DataIntegrityException;
import com.murilorb.coursespringionic.services.exception.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	public List<Customer> findAll() {
		return repository.findAll();
	}

	public Customer findById(Integer id) {
		Optional<Customer> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Customer.class.getName()));
	}

	public Customer insert(Customer obj) {
		obj.setId(null);
		return repository.save(obj);
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
			throw new DataIntegrityException("Não é possível excluir uma cliente porque há entidades relacionadas!");
		}
	}

	public Page<Customer> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Customer fromDTO(CustomerDTO objDto) {
		return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
	}

	private void updateData(Customer entity, Customer obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
	}

}
