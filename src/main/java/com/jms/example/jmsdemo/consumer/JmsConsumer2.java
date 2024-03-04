package com.jms.example.jmsdemo.consumer;

import com.jms.example.jmsdemo.model.Email;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsConsumer2 {
    @JmsListener(destination = "queue.mailbox", containerFactory = "myQueueFactory")
    public void receiveMessage(Email email) {
        System.out.println("queue.mailbox -> Received message 2: " + email);
    }
}
