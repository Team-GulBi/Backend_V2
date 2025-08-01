package com.gulbi.Backend.domain.chat.websocket;

import com.gulbi.Backend.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final WebSocketEventHandler webSocketEventHandler;
    private final UserDetailsService userDetailsService; // 사용자 정보를 가져오기 위한 서비스 추가

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // WebSocket 연결 시 헤더의 jwt token 유효성 검증
        if (StompCommand.CONNECT == accessor.getCommand()) {
            String authorization = jwtUtil.extractJwt(accessor.getFirstNativeHeader("Authorization"));
            if (authorization != null) {
                try {
                    // JWT 유효성 검사
                    String email = jwtUtil.extractEmail(authorization);
                    if (email != null) {
                        // JWT 토큰 유효성 검사 후 사용자 정보를 SecurityContext에 설정
                        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                        if (jwtUtil.validateToken(authorization, email)) {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                } catch (Exception e) {
                    log.error("JWT 토큰 유효성 검사 실패", e);
                }
            }

            // WebSocket 연결 처리
            //webSocketEventHandler.handleConnect(accessor); // WebSocketEventHandler의 handleConnect 호출
        }

//        // WebSocket 연결 종료 시 처리
//        if (StompCommand.DISCONNECT == accessor.getCommand()) {
//            webSocketEventHandler.handleDisconnect(accessor); // WebSocketEventHandler의 handleDisconnect 호출
//        }

        return message;
    }
}
