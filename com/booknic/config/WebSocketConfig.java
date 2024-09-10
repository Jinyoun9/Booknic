package com.booknic.config;

import com.booknic.handler.BookEventWebSocketHandler;
import com.booknic.interceptor.CustomHandshakeInterceptor;
import com.booknic.service.BookService;
import com.booknic.service.FavoriteService;
import com.booknic.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final BookService bookService;
    private final FavoriteService favoriteService;
    private final NotificationService notificationService;

    public WebSocketConfig(@Lazy BookService bookService, @Lazy FavoriteService favoriteService, @Lazy NotificationService notificationService) {
        this.bookService = bookService;
        this.favoriteService = favoriteService;
        this.notificationService = notificationService;
    }

    @Bean
    public BookEventWebSocketHandler bookEventWebSocketHandler() {
        return new BookEventWebSocketHandler(bookService, favoriteService, notificationService);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(bookEventWebSocketHandler(), "/ws/book-events")
                .addInterceptors(new CustomHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}

