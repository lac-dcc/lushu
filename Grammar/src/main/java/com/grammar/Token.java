package com.grammar;

import java.util.regex.Pattern;

/**
 *
 * @author vitor
 */
public class Token {

    String regex;
    boolean sensitive;

    public Token(String regex, boolean sensitive) {
        this.regex = regex;
        this.sensitive = sensitive;
    }

    public Token(String regex) {
        this(regex, false);
    }

    public String getRegex() {
        return this.regex;
    }

    public boolean getSensitive() {
        return this.sensitive;
    }

    public boolean match(String str) {
        Pattern pattern = Pattern.compile(this.regex);
        return pattern.matcher(str).matches();
    }
}
