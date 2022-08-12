package com.murilorb.coursespringionic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.murilorb.coursespringionic.domains.State;

public interface StateRepository extends JpaRepository<State, Integer> {

	@Transactional(readOnly = true)
	public List<State> findAllByOrderByName();

}
