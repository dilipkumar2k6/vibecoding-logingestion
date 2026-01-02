package com.monitoring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    // FIXED: DateTimeFormatter is thread-safe
    private static final java.time.format.DateTimeFormatter DATE_FORMAT = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // FIXED: Regex now handles flexible whitespace
    private static final Pattern LOG_PATTERN = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2})\\s+([A-Z]+)\\s+(.*)$");

    public LogEntry parse(String line) {
        if (line == null || line.isEmpty()) return null;
        
        Matcher matcher = LOG_PATTERN.matcher(line);
        if (matcher.find()) {
            try {
                LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), DATE_FORMAT);
                String level = matcher.group(2);
                String message = matcher.group(3);
                return new LogEntry(timestamp, level, message);
            } catch (Exception e) {
                // Return null for malformed dates, or could log error
                return null;
            }
        }
        return null;
    }
}
