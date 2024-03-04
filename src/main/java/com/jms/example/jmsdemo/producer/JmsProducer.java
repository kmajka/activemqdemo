package com.jms.example.jmsdemo.producer;

import com.jms.example.jmsdemo.model.Email;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsProducer {

    private final JmsMessagingTemplate jmsMessagingTemplate;

    public JmsProducer(JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    public void sendMessageToQueue(Email email) {
        this.jmsMessagingTemplate.convertAndSend("queue.mailbox", email);
    }
}
