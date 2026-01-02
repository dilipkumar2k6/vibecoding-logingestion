package com.monitoring;

public class ServerHealthTest {
    public static void main(String[] args) {
        testErrorRateCalculation();
        testSlidingWindow();
    }

    private static void testErrorRateCalculation() {
        System.out.println("Running testErrorRateCalculation...");
        ServerHealth health = new ServerHealth(msg -> {});
        health.ingest("2023-10-27 10:00:00 ERROR Fail");
        health.ingest("2023-10-27 10:00:01 INFO Success");
        
        // Expected: 0.5 (1 error / 2 total)
        double rate = health.getErrorRate();
        if (Math.abs(rate - 0.5) < 0.0001) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED: Expected 0.5, got " + rate);
        }
    }

    private static void testSlidingWindow() {
        System.out.println("Running testSlidingWindow...");
        ServerHealth health = new ServerHealth(msg -> {});
        
        // t=0: ERROR
        health.ingest("2023-10-27 10:00:00 ERROR Fail");
        // t=30s: INFO
        health.ingest("2023-10-27 10:00:30 INFO Success");
        
        // Rate should be 0.5
        if (Math.abs(health.getErrorRate() - 0.5) > 0.0001) {
            System.out.println("FAILED: Initial rate expected 0.5, got " + health.getErrorRate());
            return;
        }

        // t=61s: INFO (Should expire the first ERROR at t=0)
        // Window is 60s. Threshold for 10:01:01 is 10:00:01.
        // 10:00:00 is before 10:00:01, so it should be removed.
        health.ingest("2023-10-27 10:01:01 INFO Success");
        
        // Logs remaining: 
        // 10:00:30 INFO
        // 10:01:01 INFO
        // Error count: 0
        // Total: 2
        // Rate: 0.0
        
        double rate = health.getErrorRate();
        if (Math.abs(rate - 0.0) < 0.0001) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED: After sliding window, expected 0.0, got " + rate);
        }
    }
}
