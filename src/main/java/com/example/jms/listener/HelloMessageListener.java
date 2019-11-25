package com.example.jms.listener;

import com.example.jms.config.JmsConfig;
import com.example.jms.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloMessageListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloMessage helloMessage,
                       @Headers MessageHeaders headers, Message message) {
        /*System.out.println("I got a message");
        System.out.println(helloMessage);*/
    }


    @JmsListener(destination = JmsConfig.MY_SND_RCV_QUEUE)
    public void listenForHello(@Payload HelloMessage helloMessage,
                               @Headers MessageHeaders headers, Message message) throws JMSException {
        HelloMessage payloadMessage = HelloMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMessage);
        System.out.println("I got a message");
        System.out.println(helloMessage);
    }
}
