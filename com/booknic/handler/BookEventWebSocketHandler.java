package com.booknic.handler;

import com.booknic.entity.Notification;
import com.booknic.entity.User;
import com.booknic.jwt.JwtProvider;
import com.booknic.service.BookService;
import com.booknic.service.FavoriteService;
import com.booknic.service.NotificationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BookEventWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final BookService bookService;
    private final FavoriteService favoriteService;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, NotificationHandler> handlers = new HashMap<>();
    @PostConstruct
    public void init() {
        handlers.put("UNREAD", new UnreadNotificationHandler());
        handlers.put("ARCHIVED", new ArchivedNotificationHandler());
    }

    private void handleNotifications(List<Notification> notifications, WebSocketSession session) throws IOException {
        for (Notification notification : notifications) {
            NotificationHandler handler = determineHandler(notification);
            if (handler != null) {
                handler.handle(notification, session);
            } else {
                System.err.println("No handler determined for notification: " + notification.getContent());
            }
        }
    }

    private NotificationHandler determineHandler(Notification notification) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(7); // 7일 전
        LocalDateTime endDate = now; // 현재 날짜

        // 알림 생성일이 지정된 범위 내에 있는지 확인
        if (notification.getCreatedat().isAfter(startDate) && notification.getCreatedat().isBefore(endDate)) {
            return new ArchivedNotificationHandler(); // 새로운 알림 핸들러
        }
        else if (notification.isIsread()) {
            return new UnreadNotificationHandler(); // 읽은 알림 핸들러
        } else {
            return null;
        }
    }
    @Autowired
    public BookEventWebSocketHandler(BookService bookService, FavoriteService favoriteService, NotificationService notificationService) {
        this.bookService = bookService;
        this.favoriteService = favoriteService;
        this.notificationService = notificationService;
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        handleMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = extractTokenFromSession(session);

        if (token == null) {
            session.close(new CloseStatus(CloseStatus.NOT_ACCEPTABLE.getCode(), "No token found"));
            return;
        }

        String userId;
        try {
            userId = getUserIdFromToken(token);

        } catch (Exception e) {
            session.close(new CloseStatus(CloseStatus.NOT_ACCEPTABLE.getCode(), "Invalid token"));
            return;
        }

        if (userId == null) {
            session.close(new CloseStatus(CloseStatus.NOT_ACCEPTABLE.getCode(), "Invalid user ID"));
            return;
        }
        session.getAttributes().put("userId", userId);
        sessions.put(session.getId(), session);
        findSessionByUserId(userId);
        processArchivedNotifications(userId);
        System.out.println("WebSocket connection established for user: " + userId);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }

    public void sendBookReturn(String bookname, List<User> userList) {
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
    public void sendDueExtend(String bookname, User user){
        WebSocketSession userSession = findSessionByUserId(user.getId());
        String message = "'" + bookname + "' 의 대출 기한이 일주일 연장되었습니다.";
        if(userSession != null && userSession.isOpen()){
            try{
                userSession.sendMessage(new TextMessage(message));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            notificationService.createNotification(user.getId(), message);
        }
    }
    private WebSocketSession findSessionByUserId(String userId) {
        // Find the WebSocket session associated with the given user ID
        for (WebSocketSession session : sessions.values()) {
            if (userId.equals(session.getAttributes().get("userId"))) {
                // Get the WebSocket session for the user
                List<Notification> notifications = notificationService.getUnreadNotifications(userId);
                for (Notification notification : notifications) {
                    String payload = String.format("{\"id\": \"%d\", \"content\": \"%s\"}",
                            notification.getId(), notification.getContent());
                    TextMessage message = new TextMessage(payload);
                    System.out.println(payload);
                    try {
                        session.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return session;
            }
        }
        return null;
    }


    private String extractTokenFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                if (param.startsWith("token=")) {
                    return param.substring("token=".length());
                }
            }
        }
        return null;
    }

    private String getUserIdFromToken(String token) {
        String userid = JwtProvider.getId(token, true);
        return userid;
    }



    public void handleMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode messageData = objectMapper.readTree(message.getPayload());
            System.out.println(messageData);
            String type = messageData.path("type").asText(null);
            String content = messageData.path("content").asText(null);
            Long id = messageData.path("id").asLong();
            if (type == null || content == null) {
                // Handle missing type or content
                System.err.println("Received message with missing type or content: " + message.getPayload());
                return;
            }

            // Process the message based on the type
            switch (type) {
                case "READ_NOTIFICATION":
                    // Handle read notification
                    if (id != null) {
                        notificationService.markNotificationsAsRead(id);
                    }
                    break;
                // Handle other types if needed
                default:
                    System.err.println("Unknown message type: " + type);
                    break;
            }

        } catch (IOException e) {
            System.err.println("Error processing WebSocket message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void processArchivedNotifications(String userId){
        List<Notification> notifications = notificationService.fetchValidNotifications(userId);
        WebSocketSession session = findSessionByUserId(userId);
        if (session != null && session.isOpen()) {
            try {
                handleNotifications(notifications, session); // 핸들러를 사용해 알림 처리
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleReadNotification(String notificationId) {
        // 알림 ID를 사용하여 해당 알림의 상태를 업데이트하는 로직을 추가합니다.
        // 예: notificationService.markAsRead(notificationId);
        System.out.println("Notification with ID " + notificationId + " marked as read.");
    }
}

