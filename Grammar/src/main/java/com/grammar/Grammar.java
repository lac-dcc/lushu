package com.grammar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <h1>Grammar!</h1>
 *
 * The Grammar class represents a context-free grammar in a data structure. It
 * provides methods to build the grammar from a text string and print its
 * tokens.
 *
 * @author vitor
 * @version 1.0
 * @since 2023-03-01
 */
public class Grammar {

    /**
     * The root(non-terminal node) of the grammar.
     */
    private GrammarNode grammar;
    private MergeInterface mergeInterface;
    /**
     * Constructs a new Grammar object with an initial non-terminal node.
     */
    public Grammar() {
        mergeInterface = new MergeInterface();
        grammar = new NonTerminal(new Terminal(), null);
    }

    /**
     * Builds the grammar data structure from a text string.Splits the text
     * string into an ArrayList of strings and matches each string with the
     * nodes in the data structure.
     *
     * @param text The text string to build the grammar from.
     * @return The null if a match is found.
     */
    public ArrayList<String> build(String text) {

        ArrayList<String> input = new ArrayList<>();

        input.addAll(Arrays.asList(text.split(" ")));

        while (!input.isEmpty()) {
            grammar.conformSensitiveGrammar(input, mergeInterface);
        }
        return input;
    }

    public String conformSensitiveGrammar(String text) {
        ArrayList<String> input = new ArrayList<>();

        input.addAll(Arrays.asList(text.split(" ")));

        String outString = "";

        while (!input.isEmpty()) {
            outString += grammar.conformSensitiveGrammar(input, mergeInterface);
        }
        return outString;
    }

    /**
     * Prints the regular expressions for each token present in the grammar.
     */
    public void print() {
        grammar.print(mergeInterface);
    }
}
