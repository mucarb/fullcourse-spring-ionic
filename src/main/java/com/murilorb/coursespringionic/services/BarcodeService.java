package com.murilorb.coursespringionic.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.murilorb.coursespringionic.domains.BarcodePayment;

@Service
public class BarcodeService {

	/*
	 * metodo para preencher a data de vencimento, que sera uma semana depois do
	 * pedido
	 */
	public void fillInPaymentWithBarcode(BarcodePayment payment, Date momentOfPurchase) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(momentOfPurchase);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		payment.setDueDate(cal.getTime());
	}

}
