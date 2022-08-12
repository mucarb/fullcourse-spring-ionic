package com.murilorb.coursespringionic.domains.dtos;

import java.io.Serializable;

import com.murilorb.coursespringionic.domains.State;

public class StateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;

	public StateDTO() {
	}

	public StateDTO(State obj) {
		id = obj.getId();
		name = obj.getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
