package com.jms.example.jmsdemo.subscriber;

import com.jms.example.jmsdemo.model.Email;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsSubscriber1 {

    @JmsListener(destination = "topic.mailbox", containerFactory = "myTopicFactory")
    public void receiveMessage(Email email) {
        System.out.println("topic.mailbox -> Received message 1: " + email);
    }
}
