package com.murilorb.coursespringionic.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.murilorb.coursespringionic.domains.Customer;
import com.murilorb.coursespringionic.domains.Purchase;

@Component
public interface EmailService {

	void sendPurchaseConfirmationEmail(Purchase obj);

	void sendEmail(SimpleMailMessage msg);

	void sendPurchaseConfirmationHtmlEmail(Purchase obj);

	void sendHtmlEmail(MimeMessage msg);

	void sendNewPasswordEmail(Customer customer, String newPass);

}
