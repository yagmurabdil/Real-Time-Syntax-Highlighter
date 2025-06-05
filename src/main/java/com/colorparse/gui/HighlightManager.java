package com.colorparse.gui;

import javax.swing.text.*;
import java.awt.*;
import java.util.List;
import com.colorparse.lexer.LexicalAnalyzer;
import com.colorparse.lexer.Token;
import com.colorparse.lexer.TokenType;

public class HighlightManager {
    private StyledDocument doc;
    private SimpleAttributeSet defaultStyle, keywordStyle, stringStyle, commentStyle, 
                            numberStyle, operatorStyle, typeStyle, literalStyle, 
                            annotationStyle, preprocessorStyle, delimiterStyle, errorStyle;
    private LexicalAnalyzer lexer;
    
    public HighlightManager(StyledDocument doc) {
        this.doc = doc;
        this.lexer = new LexicalAnalyzer();
        initStyles();
    }
    
    private void initStyles() {
        // Default - White
        defaultStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(defaultStyle, Color.WHITE);
        
        // Keywords - Blue
        keywordStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(keywordStyle, new Color(86, 156, 214));
        StyleConstants.setBold(keywordStyle, true);
        
        // Types - Cyan
        typeStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(typeStyle, new Color(78, 201, 176));
        StyleConstants.setBold(typeStyle, true);
        
        // Strings - Orange
        stringStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(stringStyle, new Color(206, 145, 120));
        
        // Comments - Green
        commentStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(commentStyle, new Color(106, 153, 85));
        StyleConstants.setItalic(commentStyle, true);
        
        // Numbers - Light green
        numberStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(numberStyle, new Color(181, 206, 168));
        
        // Operators - Gray
        operatorStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(operatorStyle, new Color(212, 212, 212));
        
        // Literals (true, false, null) - Blue-purple
        literalStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(literalStyle, new Color(86, 156, 214));
        StyleConstants.setBold(literalStyle, true);
        
        // Annotations - Yellow
        annotationStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(annotationStyle, new Color(220, 220, 170));
        StyleConstants.setBold(annotationStyle, true);
        
        // Preprocessor (import, package) - Purple
        preprocessorStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(preprocessorStyle, new Color(197, 134, 192));
        StyleConstants.setBold(preprocessorStyle, true);
        
        // Delimiters - Light gray
        delimiterStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(delimiterStyle, new Color(212, 212, 212));
        
        // Errors - Red
        errorStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(errorStyle, Color.RED);
        StyleConstants.setBold(errorStyle, true);
        StyleConstants.setUnderline(errorStyle, true);
    }
    
    public void highlight(String text) {
        if (text == null || text.isEmpty()) return;
        
        try {
            // Get current document text
            String docText = doc.getText(0, doc.getLength());
            
            // First make all text default color
            doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);
            
            // Just do lexical analysis - parser is disabled for now
            List<Token> tokens = lexer.analyze(docText);
            
            // Color the tokens
            highlightTokens(tokens);
            
            System.out.println("[LOG] Highlighted " + tokens.size() + " tokens");
            
        } catch (Exception e) {
            System.err.println("Highlight error: " + e.getMessage());
        }
    }
    
    private void highlightTokens(List<Token> tokens) {
        for (Token token : tokens) {
            if (token.getType() == TokenType.WHITESPACE || token.getType() == TokenType.EOF) {
                continue; // Skip whitespace and EOF tokens
            }
            
            SimpleAttributeSet style = getStyleForTokenType(token.getType());
            
            try {
                // Make sure we don't go beyond document bounds
                int start = Math.max(0, token.getStart());
                int end = Math.min(doc.getLength(), token.getEnd());
                int length = end - start;
                
                if (length > 0 && start < doc.getLength()) {
                    doc.setCharacterAttributes(start, length, style, false);
                }
            } catch (Exception e) {
                // Ignore individual token highlighting errors
            }
        }
    }
    
    private SimpleAttributeSet getStyleForTokenType(TokenType type) {
        switch (type) {
            case KEYWORD:
                return keywordStyle;
            case TYPE:
                return typeStyle;
            case STRING:
                return stringStyle;
            case COMMENT:
                return commentStyle;
            case NUMBER:
                return numberStyle;
            case OPERATOR:
                return operatorStyle;
            case LITERAL:
                return literalStyle;
            case ANNOTATION:
                return annotationStyle;
            case PREPROCESSOR:
                return preprocessorStyle;
            case DELIMITER:
                return delimiterStyle;
            case ERROR:
                return errorStyle;
            case IDENTIFIER:
            default:
                return defaultStyle;
        }
    }
} 
 