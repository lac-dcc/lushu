package com.grammar;

import java.util.List;
/**
 * <h1>Node!</h1>
 *
 * The Node abstract class represents a node in a data structure. It provides
 * methods to match input and print node information.
 *
 * @author vitor
 * @version 1.0
 * @since 2023-03-01
 */
abstract class GrammarNode {

    /**
     * Matches the string at the head of the input text list with the tokens in the node.
     * If a match is found, returns the tail of the input list (the remaining strings after the matched string).
     *
     * @param input An List of strings representing the input text.
     * @param @return The tail of the input list (List of strings).
     */
    abstract String conformSensitiveGrammar(List<String> input, MergeInterface mergeInterface);

    /**
     * Prints the regular expressions for each token present in the node.
     *
     */
    abstract void print(MergeInterface mergeInterface);
}
