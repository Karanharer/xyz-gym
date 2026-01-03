package com.gymmanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 20)
    private String type;   // INFO, SUCCESS, WARNING, ERROR

    @Column(nullable = true)
    private LocalDate expiryDate; // Notification expiry

    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.type = "INFO";
    }

    public Notification(String message, LocalDate expiryDate) {
        this.message = message;
        this.expiryDate = expiryDate;
        this.createdAt = LocalDateTime.now();
        this.type = "INFO";
    }

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}
