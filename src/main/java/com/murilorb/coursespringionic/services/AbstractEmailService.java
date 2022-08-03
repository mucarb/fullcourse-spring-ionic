package com.murilorb.coursespringionic.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.murilorb.coursespringionic.domains.Purchase;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

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

	protected String htmlFromTemplatePurchase(Purchase obj) {
		Context context = new Context();
		context.setVariable("purchase", obj);
		return templateEngine.process("email/purchaseConfirmation", context);
	}

	@Override
	public void sendPurchaseConfirmationHtmlEmail(Purchase obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPurchase(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendPurchaseConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromPurchase(Purchase obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCustomer().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePurchase(obj), true);
		return mimeMessage;
	}

}
