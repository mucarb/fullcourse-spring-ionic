package com.murilorb.coursespringionic.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.murilorb.coursespringionic.domains.Purchase;

public interface EmailService {

	void sendPurchaseConfirmationEmail(Purchase obj);

	void sendEmail(SimpleMailMessage msg);

	void sendPurchaseConfirmationHtmlEmail(Purchase obj);

	void sendHtmlEmail(MimeMessage msg);

}
