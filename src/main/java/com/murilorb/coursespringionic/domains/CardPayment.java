package com.murilorb.coursespringionic.domains;

import javax.persistence.Entity;

import com.murilorb.coursespringionic.domains.enums.PaymentStatus;

@Entity
public class CardPayment extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer numberInstallments;

	public CardPayment() {
	}

	public CardPayment(Integer id, PaymentStatus status, Purchase purchase, Integer numberInstallments) {
		super(id, status, purchase);
		this.numberInstallments = numberInstallments;
	}

	public Integer getNumberInstallments() {
		return numberInstallments;
	}

	public void setNumberInstallments(Integer numberInstallments) {
		this.numberInstallments = numberInstallments;
	}

}
