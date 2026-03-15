package com.job.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job.dto.NotificationResponse;
import com.job.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getNotifications() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return notificationService.getNotifications(username);
    }

    @GetMapping("/unread")
    public List<NotificationResponse> getUnreadNotifications() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return notificationService.getUnreadNotifications(username);
    }

    @PutMapping("/{id}/read")
    public String markAsRead(@PathVariable Long id) {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        notificationService.markAsRead(username, id);
        return "Notification marked as read!";
    }

    @PutMapping("/read-all")
    public String markAllAsRead() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        notificationService.markAllAsRead(username);
        return "All notifications marked as read!";
    }
}