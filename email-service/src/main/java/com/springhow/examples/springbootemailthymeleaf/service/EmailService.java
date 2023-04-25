package com.springhow.examples.springbootemailthymeleaf.service;

import com.springhow.examples.springbootemailthymeleaf.model.UserAMQPDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(UserAMQPDTO userAMQPDTO) throws MessagingException {
        Context context = new Context();
        context.setVariable("userAMQPDTO", userAMQPDTO);

        String process = templateEngine.process("emails/welcome", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome " + userAMQPDTO.getName());
        helper.setText(process, true);
        helper.setTo(userAMQPDTO.getEmail());
        javaMailSender.send(mimeMessage);
    }
}
