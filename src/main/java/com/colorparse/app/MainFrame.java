package com.colorparse.app;

import javax.swing.*;
import java.awt.*;
import com.colorparse.gui.EditorPanel;

public class MainFrame extends JFrame {
    private EditorPanel editorPanel;
    
    public MainFrame() {
        setTitle("ColorParse - Real-Time Grammar-Based Syntax Highlighter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Main layout
        setLayout(new BorderLayout());
        
        // Create editor panel
        editorPanel = new EditorPanel();
        add(editorPanel, BorderLayout.CENTER);
        
        // Add status bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(new Color(30, 30, 30));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel statusLabel = new JLabel("Ready - Real-time syntax highlighting active");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusPanel.add(statusLabel);
        
        add(statusPanel, BorderLayout.SOUTH);
        
        System.out.println("[LOG] Main window ready - EditorPanel added successfully");
    }
}