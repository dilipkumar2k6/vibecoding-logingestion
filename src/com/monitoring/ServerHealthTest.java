package com.monitoring;

public class ServerHealthTest {
    public static void main(String[] args) {
        testErrorRateCalculation();
    }

    private static void testErrorRateCalculation() {
        ServerHealth health = new ServerHealth(msg -> {});
        health.ingest("2023-10-27 10:00:00 ERROR Fail");
        health.ingest("2023-10-27 10:00:01 INFO Success");
        
        // Expected: 0.5, Actual: 0.0 (due to integer division)
        double rate = health.getErrorRate();
        if (Math.abs(rate - 0.5) < 0.0001) {
            System.out.println("testErrorRateCalculation PASSED");
        } else {
            System.out.println("testErrorRateCalculation FAILED: Expected 0.5, got " + rate);
        }
    }
}
