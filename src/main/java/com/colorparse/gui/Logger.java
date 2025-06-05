package com.colorparse.gui;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    private static final String PREFIX = "[SYNTAX-HIGHLIGHTER] ";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    private Logger() {}
    
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void log(String message) {
        String timestamp = LocalTime.now().format(TIME_FORMAT);
        System.out.println(PREFIX + timestamp + " - " + message);
    }
    
    public void error(String message) {
        String timestamp = LocalTime.now().format(TIME_FORMAT);
        System.err.println(PREFIX + timestamp + " - Hata: " + message);
    }
    
    public void warn(String message) {
        String timestamp = LocalTime.now().format(TIME_FORMAT);
        System.out.println(PREFIX + timestamp + " - Uyarı: " + message);
    }
}