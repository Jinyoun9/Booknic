package com.booknic.handler;

import com.booknic.entity.Notification;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface NotificationHandler {
    void handle(Notification notification, WebSocketSession session) throws IOException;
}
