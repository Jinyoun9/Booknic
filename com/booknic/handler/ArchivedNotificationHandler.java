package com.booknic.handler;

import com.booknic.entity.Notification;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class ArchivedNotificationHandler implements NotificationHandler{

    @Override
    public void handle(Notification notification, WebSocketSession session) throws IOException {
        String payload = String.format("{\"type\": \"unread\", \"id\": \"%d\", \"content\": \"%s\"}",
                notification.getId(), notification.getContent());
        TextMessage message = new TextMessage(payload);
        session.sendMessage(message);
    }
}
