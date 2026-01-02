package com.monitoring;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleAlertService implements AlertService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void triggerAlert(String message) {
        System.out.println("[" + LocalDateTime.now().format(FORMATTER) + "] ALERT: " + message);
    }
}
