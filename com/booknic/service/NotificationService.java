package com.booknic.service;

import com.booknic.entity.Notification;
import com.booknic.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(String userId, String content) {
        Notification notification = new Notification();
        notification.setUserid(userId);
        notification.setContent(content);
        notification.setIsread(false);
        notification.setCreatedat(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(String userId) {
        return notificationRepository.findByUseridAndIsreadFalse(userId);
    }

    public void markNotificationsAsRead(Long notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setIsread(true);  // 실제 Notification 객체의 setIsread 메서드 호출
            notificationRepository.save(notification);  // 변경된 객체를 저장
        } else {
            // Notification이 존재하지 않을 경우에 대한 처리
            System.err.println("Notification with id " + notificationId + " not found.");
        }
    }

    public List<Notification> fetchValidNotifications(String userId){
        LocalDateTime startDt = LocalDateTime.now().minusDays(7);
        LocalDateTime endDt = LocalDateTime.now();
        List<Notification> validNotifications = notificationRepository.findByCreatedatBetweenAndUserid(startDt, endDt, userId);
        if (validNotifications.isEmpty()) {
            System.out.println("No notifications found.");
        } else {
            for (Notification n : validNotifications) {
                System.out.println(n.getContent());
            }
        }
        return validNotifications;
    }
}

