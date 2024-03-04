package com.jms.example.jmsdemo.controllers;

import com.jms.example.jmsdemo.model.Email;
import com.jms.example.jmsdemo.producer.JmsProducer;
import com.jms.example.jmsdemo.publisher.JmsPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JMSController {

    private final JmsProducer jmsProducer;
    private final JmsPublisher jmsPublisher;

    public JMSController(JmsProducer jmsProducer, JmsPublisher jmsPublisher) {
        this.jmsProducer = jmsProducer;
        this.jmsPublisher = jmsPublisher;
    }


    @GetMapping("send-message-queue")
    public String sendMessageQueue(@RequestParam("body") String body) {
        jmsProducer.sendMessageToQueue(new Email("info@example.com", body + " 1"));
        jmsProducer.sendMessageToQueue(new Email("info@example.com", body + " 2"));
        return "queue email: " + body;
    }

    @GetMapping("send-message-topic")
    public String sendMessageTopic(@RequestParam("body") String body) {
        jmsPublisher.sendMessageToTopic(new Email("info@example.com", body + " 1"));
        jmsPublisher.sendMessageToTopic(new Email("info@example.com", body + " 2"));
        return "topic email: " + body;
    }
}
