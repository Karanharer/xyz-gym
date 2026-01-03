package com.gymmanagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private List<String> notifications = new ArrayList<>();

    public List<String> latestActive() {
        return notifications; // dummy list
    }

    public void save(String msg, LocalDate date) {
        notifications.add(msg + " (Expiry: " + date.toString() + ")");
    }
}
