package com.monitoring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Pattern LOG_PATTERN = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}) ([A-Z]+) (.*)$");

    public LogEntry parse(String line) {
        if (line == null || line.isEmpty()) return null;
        
        Matcher matcher = LOG_PATTERN.matcher(line);
        if (matcher.find()) {
            try {
                Date date = DATE_FORMAT.parse(matcher.group(1));
                LocalDateTime timestamp = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                String level = matcher.group(2);
                String message = matcher.group(3);
                return new LogEntry(timestamp, level, message);
            } catch (ParseException e) {
                // BUG: Swallowing exception and returning null
                return null;
            }
        }
        return null;
    }
}
