package com.murilorb.coursespringionic.domains;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.murilorb.coursespringionic.domains.enums.PaymentStatus;

@Entity
// indicando para o tipo da subclasse ao instanciar pelos dados json
@JsonTypeName("barcodePayment")
public class BarcodePayment extends Payment {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date payday;

	public BarcodePayment() {
	}

	public BarcodePayment(Integer id, PaymentStatus status, Purchase purchase, Date dueDate, Date payday) {
		super(id, status, purchase);
		this.dueDate = dueDate;
		this.payday = payday;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPayday() {
		return payday;
	}

	public void setPayday(Date payday) {
		this.payday = payday;
	}

}
