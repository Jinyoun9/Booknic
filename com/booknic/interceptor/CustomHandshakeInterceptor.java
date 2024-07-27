package com.booknic.interceptor;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import java.util.Map;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = request.getURI().getQuery(); // Extract token from query params
        if (token != null && token.startsWith("token=")) {
            token = token.substring("token=".length());
            String userId = validateToken(token); // Implement token validation and user ID extraction
            if (userId != null) {
                attributes.put("userId", userId);
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // No action needed after handshake
    }

    private String validateToken(String token) {
        // Implement token validation logic here
        // Return user ID if token is valid, or null otherwise
        return "sampleUserId"; // Replace with actual user ID extraction
    }
}
