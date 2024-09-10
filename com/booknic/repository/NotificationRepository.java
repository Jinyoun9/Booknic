package com.booknic.repository;

import com.booknic.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUseridAndIsreadFalse(String userId);
    Optional<Notification> findById(Long id);
    List<Notification> findByCreatedatBetweenAndUserid(LocalDateTime startDt, LocalDateTime endDt, String userId);
}

