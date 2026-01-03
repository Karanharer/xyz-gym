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

    public List<Notification> latestActive() {
        return notificationRepository.findByExpiryDateGreaterThanEqual(LocalDate.now());
    }

    public void save(String msg, LocalDate expiryDate) {
        Notification n = new Notification();
        n.setMessage(msg);
        n.setExpiryDate(expiryDate);
        notificationRepository.save(n);
    }
}
