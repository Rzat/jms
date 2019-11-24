package com.example.jms.listener;

import com.example.jms.config.JmsConfig;
import com.example.jms.model.HelloMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class HelloMessageListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloMessage helloMessage,
                       @Headers MessageHeaders headers, Message message) {
        System.out.println("I got a message");
        System.out.println(helloMessage);
    }
}
