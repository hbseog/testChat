package com.sample.stomp.config;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class WebSocketSessionManager extends TextWebSocketHandler implements HandshakeInterceptor {

    private Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        return true; // 연결을 계속 진행
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 핸드셰이크 후 처리
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String sessionId = session.getId();
        sessionMap.put(sessionId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        String sessionId = session.getId();
        sessionMap.remove(sessionId);
    }

//    public void registerWaitingSession(String sessionId) {
//        WebSocketSession session = sessionMap.get(sessionId);
//        // 채팅 서비스에 세션 등록
//        chatService.registerWaitingSession(session);
//    }
}
