package com.monitoring;

public class Main {
    public static void main(String[] args) {
        AlertService alertService = new ConsoleAlertService();
        ServerHealth health = new ServerHealth(alertService);

        // Simulate some logs
        health.ingest("2023-10-27 10:00:00 INFO System started");
        health.ingest("2023-10-27 10:00:01 ERROR Connection failed");
        health.ingest("2023-10-27 10:00:02 INFO Retrying...");
        
        System.out.println("Current Error Rate: " + health.getErrorRate());
    }
}
