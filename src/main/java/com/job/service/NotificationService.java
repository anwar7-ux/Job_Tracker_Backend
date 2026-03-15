package com.job.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.dto.NotificationResponse;
import com.job.entity.Notification;
import com.job.entity.User;
import com.job.repository.NotificationRepository;
import com.job.repository.UserRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<NotificationResponse> getNotifications(String username) {
        User user = userRepository.findByUsername(username);
        List<Notification> notifications = notificationRepository
                .findByUserId(user.getId());

        List<NotificationResponse> response = new ArrayList<>();
        for(Notification notification : notifications) {
            NotificationResponse dto = new NotificationResponse();
            dto.setId(notification.getId());
            dto.setMessage(notification.getMessage());
            dto.setRead(notification.isRead());
            dto.setCreatedAt(notification.getCreatedAt());
            response.add(dto);
        }
        return response;
    }

    public List<NotificationResponse> getUnreadNotifications(String username) {
        User user = userRepository.findByUsername(username);
        List<Notification> notifications = notificationRepository
                .findByUserIdAndIsRead(user.getId(), false);

        List<NotificationResponse> response = new ArrayList<>();
        for(Notification notification : notifications) {
            NotificationResponse dto = new NotificationResponse();
            dto.setId(notification.getId());
            dto.setMessage(notification.getMessage());
            dto.setRead(notification.isRead());
            dto.setCreatedAt(notification.getCreatedAt());
            response.add(dto);
        }
        return response;
    }

    public void markAsRead(String username, Long notificationId) {
        Notification notification = notificationRepository
                .findById(notificationId).orElse(null);

        if(notification == null) return;
        if(!notification.getUser().getUsername().equals(username)) return;

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void markAllAsRead(String username) {
        User user = userRepository.findByUsername(username);
        List<Notification> notifications = notificationRepository
                .findByUserIdAndIsRead(user.getId(), false);

        for(Notification notification : notifications) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    public void createNotification(String username, String message) {
        User user = userRepository.findByUsername(username);

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUser(user);

        notificationRepository.save(notification);
    }
}