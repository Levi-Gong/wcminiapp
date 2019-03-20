package org.ln.wechat.miniapp.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JMSConfig {

  @Value("${spring.activemq.broker-url}")
  private String brokerUrl;

  @Value("${spring.activemq.user}")
  private String brokerUsername;

  @Value("${spring.activemq.password}")
  private String brokerPassword;

  @Bean
  public ActiveMQConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(brokerUrl);
    connectionFactory.setPassword(brokerUsername);
    connectionFactory.setUserName(brokerPassword);
    return connectionFactory;
  }

  @Bean
  public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
    MappingJackson2MessageConverter mappingJackson2MessageConverter =
        new MappingJackson2MessageConverter();
    mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
    return mappingJackson2MessageConverter;
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setMessageConverter(mappingJackson2MessageConverter());
    template.setPubSubDomain(true);
    return template;
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setConcurrency("1-1");
    factory.setPubSubDomain(true);
    return factory;
  }
}
