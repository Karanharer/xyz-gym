package com.gymmanagement.repository;

import com.gymmanagement.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    // Active notifications (expiryDate >= today or null)
    List<Notification> findByExpiryDateGreaterThanEqualOrExpiryDateIsNull(LocalDate today);
}
