package com.gymmanagement.repository;

import com.gymmanagement.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository  // 👈 हे नक्की असलं पाहिजे
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByExpiryDateGreaterThanEqual(LocalDate date);
}
