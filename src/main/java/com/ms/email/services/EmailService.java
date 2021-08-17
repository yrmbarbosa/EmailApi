package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {

        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage emailMessage = new SimpleMailMessage();

            emailMessage.setFrom(emailModel.getEmailFrom());
            emailMessage.setTo(emailModel.getEmailTo());
            emailMessage.setSubject(emailModel.getSubject());
            emailMessage.setText(emailModel.getText());
            emailSender.send(emailMessage);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException ex) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }

    }
}
