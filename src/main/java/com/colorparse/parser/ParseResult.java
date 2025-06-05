package com.colorparse.parser;

import java.util.List;

public class ParseResult {
    private final List<String> errors;
    private final int tokenCount;
    
    public ParseResult(List<String> errors) {
        this.errors = errors;
        this.tokenCount = 0;
    }
    
    public ParseResult(List<String> errors, int tokenCount) {
        this.errors = errors;
        this.tokenCount = tokenCount;
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public int getTokenCount() {
        return tokenCount;
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    
    public String getSummary() {
        return String.format("Parsed %d tokens, found %d errors", tokenCount, errors.size());
    }
}