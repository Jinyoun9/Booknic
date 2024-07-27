package com.booknic.config;

import com.booknic.handler.BookEventWebSocketHandler;
import com.booknic.interceptor.CustomHandshakeInterceptor;
import com.booknic.service.BookService;
import com.booknic.service.FavoriteService;
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

    public WebSocketConfig(@Lazy BookService bookService, FavoriteService favoriteService) {
        this.bookService = bookService;
        this.favoriteService = favoriteService;
    }

    @Bean
    public BookEventWebSocketHandler bookEventWebSocketHandler() {
        return new BookEventWebSocketHandler(bookService, favoriteService);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(bookEventWebSocketHandler(), "/ws/book-events")
                .addInterceptors(new CustomHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}

