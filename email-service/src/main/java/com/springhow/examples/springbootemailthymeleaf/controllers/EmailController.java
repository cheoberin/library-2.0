package com.springhow.examples.springbootemailthymeleaf.controllers;

import com.springhow.examples.springbootemailthymeleaf.model.User;
import com.springhow.examples.springbootemailthymeleaf.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("api/email")
@Controller
public class EmailController {


    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("register")
    @ResponseBody
    public String register(@RequestBody User user) throws MessagingException {
        emailService.sendMail(user);
        return "Email Sent Successfully.!";
    }

}
