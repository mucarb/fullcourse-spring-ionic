package com.murilorb.coursespringionic.domains.enums;

public enum CustomerType {

	PRIVATE_INDIVIDUAL(1, "Pessoa Física"), LEGAL_ENTITY(2, "Pessoa Jurídica");

	private int cod;
	private String description;

	private CustomerType(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public int getCod() {
		return cod;
	}

	public String getDesc() {
		return description;
	}

	public static CustomerType toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (CustomerType x : CustomerType.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + cod);
	}

}
