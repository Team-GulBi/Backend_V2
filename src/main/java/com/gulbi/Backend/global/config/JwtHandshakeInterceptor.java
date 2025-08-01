// 안쓰는 클래스( 리팩토링 때 삭제할 예정 )
//package com.gulbi.Backend.global.config;
//
//import com.gulbi.Backend.global.util.JwtUtil;
//import com.gulbi.Backend.domain.user.entity.User;
//import com.gulbi.Backend.domain.user.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class JwtHandshakeInterceptor implements HandshakeInterceptor {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//
//            // STOMP 헤더에서 Authorization 토큰 추출
//            List<String> authHeaders = servletRequest.getHeaders().get("Authorization");
//            if (authHeaders != null && !authHeaders.isEmpty()) {
//                String token = authHeaders.get(0).replace("Bearer ", "");
//                String email = jwtUtil.extractEmail(token);
//
//                if (jwtUtil.validateToken(token, email)) {
//                    User user = userRepository.findByEmail(email)
//                            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//
//                    // 인증 객체 생성
//                    Authentication authentication = new UsernamePasswordAuthenticationToken(
//                            user, // Principal
//                            null, // Credentials
//                            null  // Authorities (권한 제거)
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                    // SecurityContext를 attributes에 저장
//                    attributes.put("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//                    attributes.put("user", user);  // 기존 코드 유지
//                } else {
//                    throw new RuntimeException("Invalid JWT Token");
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                               WebSocketHandler wsHandler, Exception exception) {
//        // 후속 처리 - 필요한 로직이 없으면 그냥 비워둬도 됨
//    }
//}
