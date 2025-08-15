package com.gulbi.Backend.global.config;

import com.gulbi.Backend.domain.chat.websocket.StompHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    public WebSocketConfig(StompHandler stompHandler) {
        this.stompHandler = stompHandler;
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 발행경로, 프론트에서 /pub이 들어가는 경우 MessageController가 수신받음
        registry.setApplicationDestinationPrefixes("/pub");
        // 수신경로: sendToMsg 할때 /sub으로 한다면 해당 경로를 구독하는 프론트엔드한테 감
        registry.enableSimpleBroker("/queue", "/topic", "/sub"); //수신 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*");
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }

}
