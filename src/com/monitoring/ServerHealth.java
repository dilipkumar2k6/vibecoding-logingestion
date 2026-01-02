package com.monitoring;

import java.util.ArrayList;
import java.util.List;

public class ServerHealth {
    private List<LogEntry> logs = new ArrayList<>();
    private AlertService alertService;

    public ServerHealth(AlertService alertService) {
        this.alertService = alertService;
    }

    public void ingest(String logLine) {
        LogParser parser = new LogParser();
        LogEntry entry = parser.parse(logLine);
        if (entry != null) {
            logs.add(entry);
            checkHealth();
        }
    }

    private void checkHealth() {
        int errorCount = 0;
        for (LogEntry log : logs) {
            if ("ERROR".equals(log.getLevel())) {
                errorCount++;
            }
        }

        if (logs.isEmpty()) return;

        double errorRate = errorCount / logs.size();
        
        if (errorRate > 0.1) { // 10%
             alertService.triggerAlert("Error rate too high: " + errorRate);
        }
    }
    
    public double getErrorRate() {
        int errorCount = 0;
        for (LogEntry log : logs) {
            if ("ERROR".equals(log.getLevel())) {
                errorCount++;
            }
        }
        // BUG: Integer division
        return logs.isEmpty() ? 0 : errorCount / logs.size();
    }
}
