package com.grammar;

import java.util.ArrayList;
// import lushu.Merger.Merger.Token

/**
 * <h1>Terminal!</h1>
 *
 * The Terminal class represents a terminal node in a data structure. It extends
 * the Node class and provides methods to add tokens, match input, and print
 * node information.
 *
 * @author vitor
 * @version 1.0
 * @since 2023-03-01
 */
public class Terminal extends Node {

    /**
     * An ArrayList of tokens for the terminal node.
     */
    private ArrayList<Token> tokens;

    /**
     * Constructs a Terminal object with an empty ArrayList of tokens.
     */
    public Terminal() {
        tokens = new ArrayList<>();
    }

    /**
     * Adds a new token to the ArrayList of tokens.
     *
     * @param token The token to be added.
     */
    public void addToken(Token token) {
        this.tokens.add(token);
    }

    /**
     * Returns the ArrayList of tokens of the terminal node.
     *
     * @return The ArrayList of tokens.
     */
    public ArrayList<Token> getTokens() {
        return this.tokens;
    }

    /**
     * Matches the string at the head of the input text list with the tokens in
     * the terminal node. If a match is found, removes the matched string from
     * the input list and returns the remaining strings. Otherwise, creates a
     * new token and adds it to the ArrayList of tokens.
     *
     * @param input An ArrayList of strings representing the input text.
     * @return The tail of the input list (ArrayList of strings) if a match is
     * found, null otherwise.
     */
    @Override
    ArrayList<String> match(ArrayList<String> input) {
        if (input.isEmpty()) {
            return input;
        }
        String first_string = input.get(0);
        for (Token tk : this.tokens) {
            if (tk.match(first_string)) {
                input.remove(0);
                return input;
            }
        }

        // 
        tokens.add(new Token(first_string));
        input.remove(0);
        return input;
        //
    }

    @Override
    public void print() {
        this.getTokens().forEach(token -> System.out.print(token.getRegex() + " "));
        System.out.println("");
    }
}
