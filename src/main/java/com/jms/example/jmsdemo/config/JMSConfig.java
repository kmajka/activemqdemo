package com.jms.example.jmsdemo.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

@Configuration
@EnableJms
public class JMSConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("vm://localhost");
        return connectionFactory;
    }

    @Bean(name="myQueueFactory")
    public JmsListenerContainerFactory<?> myQueueFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean(name="myTopicFactory")
    public JmsListenerContainerFactory<?> myTopicFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setMessageConverter(jacksonJmsMessageConverter());
        template.setDestinationResolver(myDestinationResolver());
 //     template.setPubSubDomain(true); if we set this value we always publish/subscribe topic
        return template;
    }

    @Bean
    public DestinationResolver myDestinationResolver() {
        return new MyDestinationResolver();
    }

    private static class MyDestinationResolver extends DynamicDestinationResolver {
        @Override
        public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
                throws JMSException {
            if (destinationName.startsWith("queue.")) {
                return super.resolveDestinationName(session, destinationName.substring(6), false);
            } else if (destinationName.startsWith("topic.")) {
                return super.resolveDestinationName(session, destinationName.substring(6), true);
            }
            return super.resolveDestinationName(session, destinationName, false);
        }
    }
}