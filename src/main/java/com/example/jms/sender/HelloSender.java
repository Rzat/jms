package com.example.jms.sender;

import com.example.jms.config.JmsConfig;
import com.example.jms.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        System.out.println("I'm sending a message");
        HelloMessage message = HelloMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello world")
                .build();
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
        System.out.println("Message Sent!");
    }
}
