package com.ms.email.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;

import lombok.Data;

@Data
@Service
public class EmailService {
	//
	@Autowired
	private JavaMailSenderImpl emailSender;
	
	@Autowired
	private EmailRepository emailRepository;

	public EmailModel sendEmail(EmailModel emailModel) {
		// TODO Auto-generated method stub
		emailModel.setSendDateEmail(LocalDateTime.now());
		
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			
			String user = System.getenv("Mail");
			String pass = System.getenv("PasswdMail");
			
			System.out.println(user + "    " + pass);
			
			
			message.setFrom(emailModel.getEmailFrom());
			message.setTo(emailModel.getEmailTo());
			message.setSubject(emailModel.getSubject());
			message.setText(emailModel.getText());
			emailSender.setUsername(System.getenv("Mail"));
			emailSender.setPassword(System.getenv("PasswdMail"));
			
			emailSender.testConnection();
			
			
			emailSender.send(message);
	
		    emailModel.setStatusEmail(StatusEmail.SENT);
		} catch (MailException e) {
			emailModel.setStatusEmail(StatusEmail.ERROR);
		} finally {
			return emailRepository.save(emailModel);
		}
		
		
	}

}
