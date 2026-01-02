package com.monitoring;

import java.util.ArrayList;
import java.util.List;

public class ServerHealth {
    // Use a Deque for Sliding Window
    private final java.util.Deque<LogEntry> logs = new java.util.ArrayDeque<>();
    private final AlertService alertService;
    private static final double ERROR_THRESHOLD = 0.1; // 10%
    private static final int WINDOW_SECONDS = 60;

    public ServerHealth(AlertService alertService) {
        this.alertService = alertService;
    }

    public synchronized void ingest(String logLine) {
        LogParser parser = new LogParser();
        LogEntry entry = parser.parse(logLine);
        if (entry != null) {
            logs.addLast(entry);
            pruneOldLogs(entry.getTimestamp());
            checkHealth();
        }
    }

    private void pruneOldLogs(java.time.LocalDateTime currentTimestamp) {
        java.time.LocalDateTime threshold = currentTimestamp.minusSeconds(WINDOW_SECONDS);
        while (!logs.isEmpty() && logs.peekFirst().getTimestamp().isBefore(threshold)) {
            logs.removeFirst();
        }
    }

    private void checkHealth() {
        double errorRate = getErrorRate();
        
        if (errorRate > ERROR_THRESHOLD) {
             alertService.triggerAlert("Error rate too high: " + String.format("%.2f", errorRate));
        }
    }
    
    public synchronized double getErrorRate() {
        if (logs.isEmpty()) return 0.0;
        
        long errorCount = logs.stream()
                .filter(log -> "ERROR".equals(log.getLevel()))
                .count();
        
        return (double) errorCount / logs.size();
    }
}
