package com.colorparse.lexer;

public enum TokenType {
    // Basic token types (5+ requirement)
    KEYWORD,        // public, class, if, for, etc.
    STRING,         // "Hello World"
    COMMENT,        // // and /* */ comments
    NUMBER,         // 42, 3.14, 0xFF
    OPERATOR,       // +, -, *, /, =, ==, !=, etc.
    DELIMITER,      // ;, ,, (, ), {, }, [, ]
    IDENTIFIER,     // variable names, method names
    TYPE,           // String, Integer, Boolean, etc.
    LITERAL,        // true, false, null
    ANNOTATION,     // @Override, @Deprecated
    PREPROCESSOR,   // import, package
    ERROR,          // unknown tokens
    WHITESPACE,     // space, tab, newline
    EOF             // end of file
}
