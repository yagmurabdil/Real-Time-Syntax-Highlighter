package com.colorparse.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EditorPanel extends JPanel {
    private JTextPane textPane;
    private StyledDocument doc;
    private HighlightManager highlighter;
    private Timer timer;
    private long lastUpdateTime = 0;
    private static final long MIN_UPDATE_INTERVAL = 500; // 500ms minimum interval
    
    public EditorPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        textPane = new JTextPane();
        textPane.setBackground(Color.BLACK);
        textPane.setForeground(Color.WHITE);
        textPane.setCaretColor(Color.WHITE);
        textPane.setFont(new Font("Consolas", Font.PLAIN, 14));
        textPane.setMargin(new Insets(10, 10, 10, 10));
        
        doc = textPane.getStyledDocument();
        highlighter = new HighlightManager(doc);
        
        JScrollPane scroll = new JScrollPane(textPane);
        scroll.getViewport().setBackground(Color.BLACK);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);
        
        // Better highlighting with rate limiting
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { scheduleHighlight(); }
            public void removeUpdate(DocumentEvent e) { scheduleHighlight(); }
            public void changedUpdate(DocumentEvent e) { scheduleHighlight(); }
        });
        
        // Add some sample text to show highlighting
        SwingUtilities.invokeLater(() -> {
            textPane.setText(getSampleJavaCode());
            scheduleHighlight();
        });
        
        System.out.println("[LOG] Editor ready - Real-time highlighting is working");
    }
    
    private void scheduleHighlight() {
        long currentTime = System.currentTimeMillis();
        
        // Don't update too often
        if (currentTime - lastUpdateTime < MIN_UPDATE_INTERVAL) {
            return;
        }
        
        if (timer != null) {
            timer.cancel();
        }
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    try {
                        String text = textPane.getText();
                        if (text != null && !text.trim().isEmpty()) {
                            highlighter.highlight(text);
                            lastUpdateTime = System.currentTimeMillis();
                        }
                    } catch (Exception e) {
                        System.err.println("Highlighting error: " + e.getMessage());
                    }
                });
            }
        }, MIN_UPDATE_INTERVAL); // Wait before highlighting
    }
    
    private String getSampleJavaCode() {
        return "package com.example;\n\n" +
               "import java.util.List;\n" +
               "import java.util.ArrayList;\n\n" +
               "/**\n" +
               " * Sample Java class for syntax highlighting demo\n" +
               " */\n" +
               "@Override\n" +
               "public class HelloWorld {\n" +
               "    private static final String MESSAGE = \"Hello, World!\";\n" +
               "    private int count = 42;\n" +
               "    private boolean isActive = true;\n\n" +
               "    public static void main(String[] args) {\n" +
               "        HelloWorld app = new HelloWorld();\n" +
               "        app.printMessage();\n" +
               "        \n" +
               "        // This is a comment\n" +
               "        for (int i = 0; i < 10; i++) {\n" +
               "            System.out.println(\"Number: \" + i);\n" +
               "        }\n" +
               "    }\n\n" +
               "    private void printMessage() {\n" +
               "        if (isActive) {\n" +
               "            System.out.println(MESSAGE);\n" +
               "        } else {\n" +
               "            System.out.println(\"Inactive\");\n" +
               "        }\n" +
               "    }\n" +
               "}\n\n" +
               "// Try editing this code to see real-time highlighting!";
    }
}