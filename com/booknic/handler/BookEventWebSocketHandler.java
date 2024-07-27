package com.booknic.handler;

import com.booknic.entity.User;
import com.booknic.jwt.JwtProvider;
import com.booknic.service.BookService;
import com.booknic.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BookEventWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final BookService bookService;
    private final FavoriteService favoriteService;

    @Autowired
    public BookEventWebSocketHandler(BookService bookService, FavoriteService favoriteService) {
        this.bookService = bookService;
        this.favoriteService = favoriteService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = extractTokenFromSession(session);
        String userId = getUserIdFromToken(token); // 토큰을 통해 유저 ID 추출
        session.getAttributes().put("userId", userId);
        sessions.put(session.getId(), session);
        System.out.println("WebSocket connection established for user: " + userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }

    public void sendNotification(String bookname, List<User> userList) {
        for (User user : userList) {
            System.out.println("Sending notification for book: " + bookname + " to users: " + user);
            WebSocketSession userSession = findSessionByUserId(user.getId());
            if (userSession != null && userSession.isOpen()) {
                try {
                    String message = "The book '" + bookname + "' has been returned.";
                    userSession.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private WebSocketSession findSessionByUserId(String userId) {
        // Find the WebSocket session associated with the given user ID
        for (WebSocketSession session : sessions.values()) {
            if (userId.equals(session.getAttributes().get("userId"))) {
                return session;
            }
        }
        return null;
    }

    private String extractTokenFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        String[] params = query.split("&");
        for (String param : params) {
            if (param.startsWith("token=")) {
                return param.substring("token=".length());
            }
        }
        return null;
    }

    private String getUserIdFromToken(String token) {
        String userid = JwtProvider.getId(token, true);
        return userid;
    }
}

