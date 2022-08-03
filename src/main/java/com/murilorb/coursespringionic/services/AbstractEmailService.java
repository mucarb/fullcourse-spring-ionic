package com.murilorb.coursespringionic.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.murilorb.coursespringionic.domains.Purchase;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendPurchaseConfirmationEmail(Purchase obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPurchase(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPurchase(Purchase obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCustomer().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

}
