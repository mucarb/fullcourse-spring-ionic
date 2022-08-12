package com.murilorb.coursespringionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilorb.coursespringionic.domains.City;
import com.murilorb.coursespringionic.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;

	public List<City> findByState(Integer stateId) {
		return repository.findByStateIdOrderByNameAsc(stateId);
	}

}
