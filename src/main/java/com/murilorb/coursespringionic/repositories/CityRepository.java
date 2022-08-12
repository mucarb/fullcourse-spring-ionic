package com.murilorb.coursespringionic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.murilorb.coursespringionic.domains.City;

public interface CityRepository extends JpaRepository<City, Integer> {

	@Transactional(readOnly = true)
	public List<City> findByStateIdOrderByNameAsc(Integer stateId);

}
