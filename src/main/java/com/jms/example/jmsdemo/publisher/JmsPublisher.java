package com.jms.example.jmsdemo.publisher;

import com.jms.example.jmsdemo.model.Email;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsPublisher {

    private final JmsMessagingTemplate jmsMessagingTemplate;

    public JmsPublisher(JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    public void sendMessageToTopic(Email email) {
        this.jmsMessagingTemplate.convertAndSend("topic.mailbox", email);
    }
}
