package com.gulbi.Backend.global.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "chat.queue";
    public static final String EXCHANGE_NAME = "chat.exchange";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true); // Durable queue
    }

    // 특정 채팅방 큐를 동적으로 선언하는 메서드
    public Queue createQueue(String chatRoomId) {
        return new Queue("chat.queue." + chatRoomId, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // 동적으로 바인딩 (chat.room.{chatRoomId} 라우팅키로 바인딩)
    public Binding bindQueueToExchange(Queue queue) {
        return BindingBuilder.bind(queue).to(exchange()).with("chat.room." + queue.getName().replace("chat.queue.", ""));
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    public void createQueueForChatRoom(Long chatRoomId, RabbitAdmin rabbitAdmin) {
        String queueName = "chat.queue." + chatRoomId;
        Queue queue = new Queue(queueName, true); // durable=true 유지
        Binding binding = BindingBuilder.bind(queue).to(exchange()).with(queueName);

        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
    }
}
