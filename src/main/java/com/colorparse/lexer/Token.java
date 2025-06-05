package com.colorparse.lexer;

public class Token {
    private TokenType type;
    private String value;
    private int start;
    private int end;
    private int line;
    private int column;
    
    public Token(TokenType type, String value, int start, int end) {
        this.type = type;
        this.value = value;
        this.start = start;
        this.end = end;
        this.line = 1;
        this.column = 1;
    }
    
    public Token(TokenType type, String value, int start, int end, int line, int column) {
        this.type = type;
        this.value = value;
        this.start = start;
        this.end = end;
        this.line = line;
        this.column = column;
    }
    
    // Getters
    public TokenType getType() { return type; }
    public String getValue() { return value; }
    public int getStart() { return start; }
    public int getEnd() { return end; }
    public int getLine() { return line; }
    public int getColumn() { return column; }
    
    // Setters
    public void setLine(int line) { this.line = line; }
    public void setColumn(int column) { this.column = column; }
    
    @Override
    public String toString() {
        return String.format("Token{type=%s, value='%s', pos=%d-%d, line=%d, col=%d}", 
                           type, value, start, end, line, column);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Token token = (Token) obj;
        return start == token.start && end == token.end && 
               type == token.type && value.equals(token.value);
    }
}