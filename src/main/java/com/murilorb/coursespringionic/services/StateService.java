package com.murilorb.coursespringionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilorb.coursespringionic.domains.State;
import com.murilorb.coursespringionic.repositories.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository repository;

	public List<State> findAll() {
		return repository.findAllByOrderByName();
	}

}
