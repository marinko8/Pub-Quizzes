package com.example.Pubquizspringboot.service;

import com.example.Pubquizspringboot.model.NotifikacijskiEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private JavaMailSender mailSender;
    private MailContentBuilder mailContentBuilder;

    @Async
    void sendMail(NotifikacijskiEmail notifikacijskiEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("knezevic.marin85@gmail.com");
            messageHelper.setTo(notifikacijskiEmail.getPrimatelj());
            messageHelper.setSubject(notifikacijskiEmail.getPredmet());
            messageHelper.setText(notifikacijskiEmail.getSadrzaj());
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            e.getStackTrace();
        }
    }
}
