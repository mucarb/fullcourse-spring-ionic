package com.murilorb.coursespringionic.domains.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

public class ProductNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String name;
	@NotNull(message = "Preenchimento obrigatório")
	@Positive(message = "Preço inválido")
	private Double price;
	@NotEmpty(message = "Informar ao menos uma Categoria para este produto")
	private List<Integer> categoriesId = new ArrayList<>();

	public ProductNewDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Integer> getCategoriesId() {
		return categoriesId;
	}

}
