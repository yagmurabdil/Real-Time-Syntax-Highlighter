package com.colorparse.parser;

import java.util.List;
import java.util.ArrayList;
import com.colorparse.lexer.Token;
import com.colorparse.lexer.TokenType;

public class SyntaxParser {
    private List<Token> tokens;
    private int currentIndex;
    private List<String> errors;
    
    public ParseResult parse(List<Token> tokens) {
        this.tokens = tokens;
        this.currentIndex = 0;
        this.errors = new ArrayList<>();
        
        try {
            // Simple syntax check - just basic errors
            performBasicSyntaxCheck();
        } catch (Exception e) {
            errors.add("Critical parsing error: " + e.getMessage());
        }
        
        System.out.println("[LOG] Syntax Analysis: " + errors.size() + " errors found");
        return new ParseResult(errors, tokens.size());
    }
    
    private void performBasicSyntaxCheck() {
        // Simple checks - minimal for performance
        for (int i = 0; i < tokens.size() - 1; i++) {
            Token current = tokens.get(i);
            Token next = tokens.get(i + 1);
            
            // Simple syntax rules
            if (current.getType() == TokenType.KEYWORD && "public".equals(current.getValue())) {
                if (next.getType() != TokenType.KEYWORD || 
                    (!"class".equals(next.getValue()) && !"static".equals(next.getValue()) && 
                     !"interface".equals(next.getValue()) && !"enum".equals(next.getValue()))) {
                    errors.add("Line " + current.getLine() + ": Expected 'class', 'interface', 'enum', or 'static' after 'public'");
                }
            }
            
            // Parenthesis check
            if (current.getType() == TokenType.DELIMITER && "(".equals(current.getValue())) {
                // Look for closing parenthesis
                boolean foundClosing = false;
                for (int j = i + 1; j < tokens.size(); j++) {
                    if (tokens.get(j).getType() == TokenType.DELIMITER && ")".equals(tokens.get(j).getValue())) {
                        foundClosing = true;
                        break;
                    }
                }
                if (!foundClosing) {
                    errors.add("Line " + current.getLine() + ": Missing closing parenthesis");
                }
            }
        }
    }
    
    private void parseCompilationUnit() {
        // Skip whitespace
        skipWhitespace();
        
        // Optional package declaration
        if (currentTokenIs(TokenType.PREPROCESSOR, "package")) {
            parsePackageDeclaration();
        }
        
        // Import declarations
        while (currentTokenIs(TokenType.PREPROCESSOR, "import")) {
            parseImportDeclaration();
        }
        
        // Type declarations (class, interface, enum)
        while (!isAtEnd()) {
            skipWhitespace();
            if (isAtEnd()) break;
            
            parseTypeDeclaration();
        }
    }
    
    private void parsePackageDeclaration() {
        consume(TokenType.PREPROCESSOR, "package", "Expected 'package'");
        skipWhitespace();
        
        if (!currentTokenIs(TokenType.IDENTIFIER)) {
            addError("Expected package name after 'package'");
            return;
        }
        
        // Parse qualified name (com.example.package)
        parseQualifiedName();
        skipWhitespace();
        
        consume(TokenType.DELIMITER, ";", "Expected ';' after package declaration");
    }
    
    private void parseImportDeclaration() {
        consume(TokenType.PREPROCESSOR, "import", "Expected 'import'");
        skipWhitespace();
        
        // Optional static
        if (currentTokenIs(TokenType.KEYWORD, "static")) {
            advance();
            skipWhitespace();
        }
        
        parseQualifiedName();
        skipWhitespace();
        
        // Optional .*
        if (currentTokenIs(TokenType.DELIMITER, ".")) {
            advance();
            if (currentTokenIs(TokenType.OPERATOR, "*")) {
                advance();
            }
        }
        
        skipWhitespace();
        consume(TokenType.DELIMITER, ";", "Expected ';' after import declaration");
    }
    
    private void parseTypeDeclaration() {
        skipWhitespace();
        
        // Parse modifiers
        parseModifiers();
        
        if (currentTokenIs(TokenType.KEYWORD, "class")) {
            parseClassDeclaration();
        } else if (currentTokenIs(TokenType.KEYWORD, "interface")) {
            parseInterfaceDeclaration();
        } else if (currentTokenIs(TokenType.KEYWORD, "enum")) {
            parseEnumDeclaration();
        } else {
            addError("Expected class, interface, or enum declaration");
            skipToNextDeclaration();
        }
    }
    
    private void parseClassDeclaration() {
        consume(TokenType.KEYWORD, "class", "Expected 'class'");
        skipWhitespace();
        
        if (!currentTokenIs(TokenType.IDENTIFIER)) {
            addError("Expected class name after 'class'");
            return;
        }
        
        advance(); // class name
        skipWhitespace();
        
        // Optional extends
        if (currentTokenIs(TokenType.KEYWORD, "extends")) {
            advance();
            skipWhitespace();
            if (!currentTokenIs(TokenType.TYPE) && !currentTokenIs(TokenType.IDENTIFIER)) {
                addError("Expected superclass name after 'extends'");
            } else {
                advance();
            }
            skipWhitespace();
        }
        
        // Optional implements
        if (currentTokenIs(TokenType.KEYWORD, "implements")) {
            advance();
            skipWhitespace();
            parseInterfaceList();
        }
        
        // Class body
        consume(TokenType.DELIMITER, "{", "Expected '{' to start class body");
        parseClassBody();
        consume(TokenType.DELIMITER, "}", "Expected '}' to end class body");
    }
    
    private void parseInterfaceDeclaration() {
        consume(TokenType.KEYWORD, "interface", "Expected 'interface'");
        skipWhitespace();
        
        if (!currentTokenIs(TokenType.IDENTIFIER)) {
            addError("Expected interface name after 'interface'");
            return;
        }
        
        advance(); // interface name
        skipWhitespace();
        
        // Optional extends
        if (currentTokenIs(TokenType.KEYWORD, "extends")) {
            advance();
            skipWhitespace();
            parseInterfaceList();
        }
        
        // Interface body
        consume(TokenType.DELIMITER, "{", "Expected '{' to start interface body");
        parseInterfaceBody();
        consume(TokenType.DELIMITER, "}", "Expected '}' to end interface body");
    }
    
    private void parseEnumDeclaration() {
        consume(TokenType.KEYWORD, "enum", "Expected 'enum'");
        skipWhitespace();
        
        if (!currentTokenIs(TokenType.IDENTIFIER)) {
            addError("Expected enum name after 'enum'");
            return;
        }
        
        advance(); // enum name
        skipWhitespace();
        
        // Enum body
        consume(TokenType.DELIMITER, "{", "Expected '{' to start enum body");
        parseEnumBody();
        consume(TokenType.DELIMITER, "}", "Expected '}' to end enum body");
    }
    
    private void parseModifiers() {
        while (currentTokenIs(TokenType.KEYWORD) && isModifier(getCurrentToken().getValue())) {
            advance();
            skipWhitespace();
        }
        
        // Annotations
        while (currentTokenIs(TokenType.ANNOTATION)) {
            advance();
            skipWhitespace();
        }
    }
    
    private boolean isModifier(String value) {
        return value.equals("public") || value.equals("private") || value.equals("protected") ||
               value.equals("static") || value.equals("final") || value.equals("abstract") ||
               value.equals("synchronized") || value.equals("native") || value.equals("strictfp") ||
               value.equals("transient") || value.equals("volatile");
    }
    
    private void parseQualifiedName() {
        if (!currentTokenIs(TokenType.IDENTIFIER)) {
            addError("Expected identifier in qualified name");
            return;
        }
        
        advance();
        
        while (currentTokenIs(TokenType.DELIMITER, ".")) {
            advance();
            skipWhitespace();
            if (!currentTokenIs(TokenType.IDENTIFIER)) {
                addError("Expected identifier after '.' in qualified name");
                break;
            }
            advance();
        }
    }
    
    private void parseInterfaceList() {
        parseQualifiedName();
        skipWhitespace();
        
        while (currentTokenIs(TokenType.DELIMITER, ",")) {
            advance();
            skipWhitespace();
            parseQualifiedName();
            skipWhitespace();
        }
    }
    
    private void parseClassBody() {
        skipWhitespace();
        
        while (!currentTokenIs(TokenType.DELIMITER, "}") && !isAtEnd()) {
            parseClassMember();
            skipWhitespace();
        }
    }
    
    private void parseInterfaceBody() {
        skipWhitespace();
        
        while (!currentTokenIs(TokenType.DELIMITER, "}") && !isAtEnd()) {
            parseInterfaceMember();
            skipWhitespace();
        }
    }
    
    private void parseEnumBody() {
        skipWhitespace();
        
        // Parse enum constants
        if (currentTokenIs(TokenType.IDENTIFIER)) {
            advance();
            skipWhitespace();
            
            while (currentTokenIs(TokenType.DELIMITER, ",")) {
                advance();
                skipWhitespace();
                if (currentTokenIs(TokenType.IDENTIFIER)) {
                    advance();
                    skipWhitespace();
                }
            }
            
            if (currentTokenIs(TokenType.DELIMITER, ";")) {
                advance();
                skipWhitespace();
            }
        }
        
        // Parse enum body (methods, fields)
        while (!currentTokenIs(TokenType.DELIMITER, "}") && !isAtEnd()) {
            parseClassMember();
            skipWhitespace();
        }
    }
    
    private void parseClassMember() {
        parseModifiers();
        
        // Constructor, method, or field
        if (currentTokenIs(TokenType.IDENTIFIER)) {
            // Could be constructor or method
            advance();
            skipWhitespace();
            
            if (currentTokenIs(TokenType.DELIMITER, "(")) {
                // Method or constructor
                parseMethodDeclaration();
            } else {
                // Field
                parseFieldDeclaration();
            }
        } else {
            skipToNextMember();
        }
    }
    
    private void parseInterfaceMember() {
        parseModifiers();
        
        if (currentTokenIs(TokenType.IDENTIFIER) || currentTokenIs(TokenType.TYPE) || currentTokenIs(TokenType.KEYWORD, "void")) {
            parseMethodDeclaration();
        } else {
            skipToNextMember();
        }
    }
    
    private void parseMethodDeclaration() {
        // Skip to end of method for now (simplified)
        int braceCount = 0;
        while (!isAtEnd()) {
            if (currentTokenIs(TokenType.DELIMITER, "{")) {
                braceCount++;
            } else if (currentTokenIs(TokenType.DELIMITER, "}")) {
                braceCount--;
                if (braceCount < 0) break;
            } else if (currentTokenIs(TokenType.DELIMITER, ";") && braceCount == 0) {
                advance();
                break;
            }
            advance();
        }
    }
    
    private void parseFieldDeclaration() {
        // Skip to semicolon
        while (!currentTokenIs(TokenType.DELIMITER, ";") && !isAtEnd()) {
            advance();
        }
        if (currentTokenIs(TokenType.DELIMITER, ";")) {
            advance();
        }
    }
    
    // Utility methods
    private Token getCurrentToken() {
        if (isAtEnd()) return null;
        return tokens.get(currentIndex);
    }
    
    private boolean currentTokenIs(TokenType type) {
        Token token = getCurrentToken();
        return token != null && token.getType() == type;
    }
    
    private boolean currentTokenIs(TokenType type, String value) {
        Token token = getCurrentToken();
        return token != null && token.getType() == type && token.getValue().equals(value);
    }
    
    private void advance() {
        if (!isAtEnd()) {
            currentIndex++;
        }
    }
    
    private boolean isAtEnd() {
        return currentIndex >= tokens.size() || 
               (getCurrentToken() != null && getCurrentToken().getType() == TokenType.EOF);
    }
    
    private void skipWhitespace() {
        while (currentTokenIs(TokenType.WHITESPACE)) {
            advance();
        }
    }
    
    private void consume(TokenType type, String value, String errorMessage) {
        if (currentTokenIs(type, value)) {
            advance();
        } else {
            addError(errorMessage + " (found: " + (getCurrentToken() != null ? getCurrentToken().getValue() : "EOF") + ")");
        }
    }
    
    private void addError(String message) {
        Token token = getCurrentToken();
        if (token != null) {
            errors.add("Line " + token.getLine() + ", Column " + token.getColumn() + ": " + message);
        } else {
            errors.add("End of file: " + message);
        }
    }
    
    private void skipToNextDeclaration() {
        while (!isAtEnd() && !currentTokenIs(TokenType.KEYWORD, "class") && 
               !currentTokenIs(TokenType.KEYWORD, "interface") && !currentTokenIs(TokenType.KEYWORD, "enum")) {
            advance();
        }
    }
    
    private void skipToNextMember() {
        while (!isAtEnd() && !currentTokenIs(TokenType.DELIMITER, "}")) {
            if (currentTokenIs(TokenType.DELIMITER, ";")) {
                advance();
                break;
            }
            advance();
        }
    }
}