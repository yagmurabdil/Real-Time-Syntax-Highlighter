package com.colorparse.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    
    // Regex patterns for different token types
    private static final Pattern COMMENT_SINGLE = Pattern.compile("//.*");
    private static final Pattern COMMENT_MULTI = Pattern.compile("/\\*[\\s\\S]*?\\*/");
    private static final Pattern STRING_LITERAL = Pattern.compile("\"([^\"\\\\]|\\\\.)*\"");
    private static final Pattern CHAR_LITERAL = Pattern.compile("'([^'\\\\]|\\\\.)*'");
    private static final Pattern NUMBER = Pattern.compile("\\b\\d+(\\.\\d+)?([eE][+-]?\\d+)?[fFdDlL]?\\b|0[xX][0-9a-fA-F]+[lL]?\\b");
    private static final Pattern ANNOTATION = Pattern.compile("@\\w+");
    private static final Pattern IDENTIFIER = Pattern.compile("\\b[a-zA-Z_$][a-zA-Z0-9_$]*\\b");
    private static final Pattern OPERATOR = Pattern.compile("(\\+\\+|--|\\+=|-=|\\*=|/=|%=|&=|\\|=|\\^=|<<=|>>=|>>>=|==|!=|<=|>=|&&|\\|\\||<<|>>|>>>|\\+|-|\\*|/|%|&|\\||\\^|~|<|>|=|!|\\?)");
    private static final Pattern DELIMITER = Pattern.compile("[;,(){}\\[\\].]");
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    
    // Java keywords
    private static final String[] KEYWORDS = {
        "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
        "class", "const", "continue", "default", "do", "double", "else", "enum",
        "extends", "final", "finally", "float", "for", "goto", "if", "implements",
        "import", "instanceof", "int", "interface", "long", "native", "new", "package",
        "private", "protected", "public", "return", "short", "static", "strictfp",
        "super", "switch", "synchronized", "this", "throw", "throws", "transient",
        "try", "void", "volatile", "while"
    };
    
    // Java types
    private static final String[] TYPES = {
        "String", "Integer", "Double", "Float", "Boolean", "Character", "Byte",
        "Short", "Long", "Object", "System", "Math", "Exception", "Thread",
        "JFrame", "JPanel", "JButton", "JLabel", "JTextField", "ArrayList",
        "HashMap", "List", "Map", "Set"
    };
    
    // Java literals
    private static final String[] LITERALS = {
        "true", "false", "null"
    };
    
    // Preprocessor stuff
    private static final String[] PREPROCESSORS = {
        "import", "package"
    };
    
    public List<Token> analyze(String text) {
        List<Token> tokens = new ArrayList<>();
        
        if (text == null || text.isEmpty()) {
            return tokens;
        }
        
        int position = 0;
        int line = 1;
        int column = 1;
        
        while (position < text.length()) {
            Token token = getNextToken(text, position, line, column);
            
            if (token != null) {
                tokens.add(token);
                position = token.getEnd();
                
                // Update line and column
                String tokenValue = token.getValue();
                for (char c : tokenValue.toCharArray()) {
                    if (c == '\n') {
                        line++;
                        column = 1;
                    } else {
                        column++;
                    }
                }
            } else {
                // Skip unknown character
                position++;
                column++;
            }
        }
        
        // Add EOF token
        tokens.add(new Token(TokenType.EOF, "", position, position, line, column));
        
        System.out.println("[LOG] Lexical Analysis: " + tokens.size() + " tokens found");
        return tokens;
    }
    
    private Token getNextToken(String text, int position, int line, int column) {
        if (position >= text.length()) {
            return null;
        }
        
        String remaining = text.substring(position);
        
        // Check for multi-line comments first
        Matcher commentMultiMatcher = COMMENT_MULTI.matcher(remaining);
        if (commentMultiMatcher.lookingAt()) {
            String value = commentMultiMatcher.group();
            return new Token(TokenType.COMMENT, value, position, position + value.length(), line, column);
        }
        
        // Check for single-line comments
        Matcher commentSingleMatcher = COMMENT_SINGLE.matcher(remaining);
        if (commentSingleMatcher.lookingAt()) {
            String value = commentSingleMatcher.group();
            return new Token(TokenType.COMMENT, value, position, position + value.length(), line, column);
        }
        
        // Check for string literals
        Matcher stringMatcher = STRING_LITERAL.matcher(remaining);
        if (stringMatcher.lookingAt()) {
            String value = stringMatcher.group();
            return new Token(TokenType.STRING, value, position, position + value.length(), line, column);
        }
        
        // Check for character literals
        Matcher charMatcher = CHAR_LITERAL.matcher(remaining);
        if (charMatcher.lookingAt()) {
            String value = charMatcher.group();
            return new Token(TokenType.STRING, value, position, position + value.length(), line, column);
        }
        
        // Check for numbers
        Matcher numberMatcher = NUMBER.matcher(remaining);
        if (numberMatcher.lookingAt()) {
            String value = numberMatcher.group();
            return new Token(TokenType.NUMBER, value, position, position + value.length(), line, column);
        }
        
        // Check for annotations
        Matcher annotationMatcher = ANNOTATION.matcher(remaining);
        if (annotationMatcher.lookingAt()) {
            String value = annotationMatcher.group();
            return new Token(TokenType.ANNOTATION, value, position, position + value.length(), line, column);
        }
        
        // Check for operators
        Matcher operatorMatcher = OPERATOR.matcher(remaining);
        if (operatorMatcher.lookingAt()) {
            String value = operatorMatcher.group();
            return new Token(TokenType.OPERATOR, value, position, position + value.length(), line, column);
        }
        
        // Check for delimiters
        Matcher delimiterMatcher = DELIMITER.matcher(remaining);
        if (delimiterMatcher.lookingAt()) {
            String value = delimiterMatcher.group();
            return new Token(TokenType.DELIMITER, value, position, position + value.length(), line, column);
        }
        
        // Check for whitespace
        Matcher whitespaceMatcher = WHITESPACE.matcher(remaining);
        if (whitespaceMatcher.lookingAt()) {
            String value = whitespaceMatcher.group();
            return new Token(TokenType.WHITESPACE, value, position, position + value.length(), line, column);
        }
        
        // Check for identifiers (must be after operators and delimiters)
        Matcher identifierMatcher = IDENTIFIER.matcher(remaining);
        if (identifierMatcher.lookingAt()) {
            String value = identifierMatcher.group();
            TokenType type = classifyIdentifier(value);
            return new Token(type, value, position, position + value.length(), line, column);
        }
        
        // Unknown character - create error token
        char unknownChar = text.charAt(position);
        return new Token(TokenType.ERROR, String.valueOf(unknownChar), position, position + 1, line, column);
    }
    
    private TokenType classifyIdentifier(String identifier) {
        // Check if it's a keyword
        for (String keyword : KEYWORDS) {
            if (keyword.equals(identifier)) {
                return TokenType.KEYWORD;
            }
        }
        
        // Check if it's a type
        for (String type : TYPES) {
            if (type.equals(identifier)) {
                return TokenType.TYPE;
            }
        }
        
        // Check if it's a literal
        for (String literal : LITERALS) {
            if (literal.equals(identifier)) {
                return TokenType.LITERAL;
            }
        }
        
        // Check if it's a preprocessor thing
        for (String preprocessor : PREPROCESSORS) {
            if (preprocessor.equals(identifier)) {
                return TokenType.PREPROCESSOR;
            }
        }
        
        // Default to identifier
        return TokenType.IDENTIFIER;
    }
}