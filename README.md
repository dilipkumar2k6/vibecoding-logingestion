# Server Health Monitoring - Interview Practice

## Scenario
You are building a backend service that monitors server health based on log files. You have been given a codebase that parses logs, but it’s incomplete and buggy. Your goal is to fix the parser, implement a health metric, and add an alerting feature.

## Goals
1.  **Fix the Bugs**: The current implementation has several bugs (parsing, thread safety, logic). Find and fix them.
2.  **Verify**: Ensure `ServerHealthTest` passes. Add more tests if needed.
3.  **Extend**:
    -   Implement a **Sliding Window** for the error rate (e.g., error rate over the last 1 minute).
    -   Implement the `AlertService` to print to console or collect alerts.
    -   (Optional) Add support for different log levels or more complex patterns.

## Getting Started
The code is located in `src/com/monitoring`.

### Compilation
```bash
mkdir -p out
javac -d out src/com/monitoring/*.java
```

### Running
```bash
java -cp out com.monitoring.Main
java -cp out com.monitoring.ServerHealthTest
```

## Known Issues (Hints)
-   Why is the error rate always 0?
-   Is `SimpleDateFormat` safe to use this way?
-   What happens if the log format changes slightly (e.g., extra spaces)?
