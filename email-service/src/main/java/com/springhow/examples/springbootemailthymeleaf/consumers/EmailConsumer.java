package com.springhow.examples.springbootemailthymeleaf.consumers;

import com.springhow.examples.springbootemailthymeleaf.model.UserAMQPDTO;
import com.springhow.examples.springbootemailthymeleaf.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload UserAMQPDTO userAMQPDTO) throws MessagingException {
        System.out.println(userAMQPDTO.getUsername() + " " + userAMQPDTO.getName() + " " + userAMQPDTO.getEmail());
        emailService.sendMail(userAMQPDTO);
    }

}
