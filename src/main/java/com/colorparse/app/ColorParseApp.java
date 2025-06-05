package com.colorparse.app;

import javax.swing.*;
import java.awt.*;


public class ColorParseApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Failed to set look and feel: " + e.getMessage());
            }
            
            // Create and show main app window
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            
            System.out.println("ColorParse started successfully.");
        });
    }
} 