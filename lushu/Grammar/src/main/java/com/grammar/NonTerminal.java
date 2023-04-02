package com.grammar;

import java.util.ArrayList;

/**
 * <h1>NonTerminal!</h1>
 *
 * The NonTerminal class represents a non-terminal node in a data structure. It
 * extends the Node class and provides methods to match input and print node
 * information.
 *
 * @author vitor
 * @version 1.0
 * @since 2023-03-01
 */
public class NonTerminal extends Node {

    /**
     * The first node in the non-terminal. This will be a terminal node.
     */
    final private Node first;
    /**
     * The second node in the non-terminal. This will be a non-terminal node.
     */
    private Node second;

    /**
     * Constructs a NonTerminal object with the specified first and second
     * nodes.
     *
     * @param first The first node in the non-terminal.
     * @param second The second node in the non-terminal.
     */
    public NonTerminal(Node first, Node second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Matches the string at the head of the input text list with the tokens in
     * the first node. If a match is found, returns the tail of the input list
     * (the remaining strings after the matched string). Otherwise, creates a
     * new terminal node and tries to match again. If there is a second node,
     * passes the tail of the input list to it for further matching.
     *
     * @param input An ArrayList of strings representing the input text.
     * @return The tail of the input list (ArrayList of strings) if a match is
     * found, null otherwise.
     */
    @Override
    ArrayList<String> match(ArrayList<String> input) {
        ArrayList<String> tail = first.match(input);

        if (tail.isEmpty()) {
            return tail;
        }
        if (second == null) {
            second = new NonTerminal(new Terminal(), null);
        }

        return second.match(tail);
    }
    
    /**
     * Prints the regular expressions for each token present in the
     * terminal node and call to the next non-terminal node.
     */
    @Override
    public void print() {
        first.print();
        if (second != null) {
            second.print();
        }
    }
}
