package com.example.jms.sender;

import com.example.jms.config.JmsConfig;
import com.example.jms.model.HelloMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

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

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        HelloMessage message = HelloMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();
        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SND_RCV_QUEUE, new MessageCreator() {
            //Convenience methods for sending messages to and receiving the reply from a destination
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));

                    helloMessage.setStringProperty("_type", "com.example.jms.model.HelloMessage");
                    System.out.println("Sending hello");
                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException("boom");
                }
            }
        });
        System.out.println("received message :->" + receivedMessage.getBody(String.class));
    }


}
