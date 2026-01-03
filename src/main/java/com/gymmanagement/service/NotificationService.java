package com.gymmanagement.service;

import com.gymmanagement.model.Notification;
import com.gymmanagement.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Active notifications for dashboard
    public List<Notification> latestActive() {
        return notificationRepository.findByExpiryDateGreaterThanEqualOrExpiryDateIsNull(LocalDate.now());
    }

    // Save new notification with expiry date
    public void save(String message, LocalDate expiryDate) {
        Notification n = new Notification(message, expiryDate);
        notificationRepository.save(n);
    }
}
