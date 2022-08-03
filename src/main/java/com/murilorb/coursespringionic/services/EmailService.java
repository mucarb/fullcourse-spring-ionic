package com.murilorb.coursespringionic.services;

import org.springframework.mail.SimpleMailMessage;

import com.murilorb.coursespringionic.domains.Purchase;

public interface EmailService {

	void sendPurchaseConfirmationEmail(Purchase obj);

	void sendEmail(SimpleMailMessage msg);

}
