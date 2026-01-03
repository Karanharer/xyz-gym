package com.gymmanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    // ================= CONSTRUCTORS =================

    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.type = "INFO";
    }

    public Notification(String message) {
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.type = "INFO";
    }

    public Notification(String message, String type) {
        this.message = message;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    // ================= GETTERS & SETTERS =================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

