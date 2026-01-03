package com.gymmanagement.repository;

import com.gymmanagement.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    // Active notifications (expiryDate >= today)
    List<Notification> findByExpiryDateGreaterThanEqual(LocalDate date);
}
