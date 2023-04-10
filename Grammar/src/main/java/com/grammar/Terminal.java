package com.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lushu.Merger.Lattice.Node.IntervalNode;
import lushu.Merger.Lattice.Node.Node;

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
public class Terminal extends GrammarNode {

    /**
     * An List of tokens for the terminal node.
     */
    private List<? extends Node> tokens;

    /**
     * Constructs a Terminal object with an empty List of tokens.
     */
    public Terminal() {
        tokens = new ArrayList<>();
    }

    /**
     * Adds a new token to the List of tokens.
     *
     * @param string
     * @param flagSensitive
     * @param mi
     */
    public void addToken(String string, boolean flagSensitive, MergeInterface mi) {
        List<IntervalNode> tokenAux = mi.getNodeFactory().buildIntervalNodes(string, flagSensitive);
        if (tokens.isEmpty()) {
            this.tokens = mi.getMerger().merge(tokenAux, tokenAux);

        } else {
            List<? extends Node> tkResult;
            tkResult = mi.getMerger().merge(this.tokens, tokenAux);
            if (tkResult.size() == 1 && mi.isTop(tkResult.get(0))) {
                List<? extends Node> tkAux;
                tkAux = mi.getMerger().merge(tokenAux, tokenAux);
                List<Node> nodeList = new ArrayList<>();
                nodeList.addAll(tokens);
                nodeList.add(tkAux.get(0));
                this.tokens = nodeList;
            } else {
                this.tokens = tkResult;
            }
        }

    }

    /**
     * Returns the List of tokens of the terminal node.
     *
     * @return The List of tokens.
     */
    public List<? extends Node> getTokens() {
        return this.tokens;
    }

    private Node match(String str, boolean flagSensitive, MergeInterface mi) {
        if(str.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}") && flagSensitive==false){
            ArrayList<String> aux = new ArrayList<>();
            aux.addAll(Arrays.asList(str.split("\\.")));
            str = aux.get(0);
        }
        Node tkAux = null;
        for (Node tk : this.tokens) {
            if (mi.matchToken(tk, str)) {
                if (tk.getSensitive()) {
                    return tk;
                } else if (tk.getSensitive() == flagSensitive) {
                    tkAux = tk;
                }
            }
        }
        return tkAux;
    }

    private boolean hasToken(String str) {
        Pattern pattern = Pattern.compile("<s>.*</s>");
        return pattern.matcher(str).matches();
    }

    /**
     * Matches the string at the head of the input text list with the tokens in
     * the terminal node. If a match is found, removes the matched string from
     * the input list and returns the remaining strings. Otherwise, creates a
     * new token and adds it to the List of tokens.
     *
     * @param input An List of strings representing the input text.
     * @return The tail of the input list (List of strings) if a match is found,
     * null otherwise.
     */
    @Override
    String conformSensitiveGrammar(List<String> input, MergeInterface mergeInterface) {
        if (input.isEmpty()) {
            return "";
        }

        boolean flagSensitive = false;

        String firstString = input.get(0);

        if (firstString.charAt(0) == '<') {
            if (hasToken(firstString)) {
                firstString = firstString.substring(3, firstString.length() - 4);
                flagSensitive = true;
            }
        }

        Node tk = match(firstString, flagSensitive, mergeInterface);
        
        if (tk == null) {
            addToken(firstString, flagSensitive, mergeInterface);
            if (flagSensitive) {
                return "*".repeat(firstString.length());
            }
            return firstString;
        } else if (tk.getSensitive() == flagSensitive) {
            if (flagSensitive) {
                return "*".repeat(firstString.length());
            }
            return firstString;
        } else if (tk.getSensitive() == false && flagSensitive == true) {
            addToken(firstString, flagSensitive, mergeInterface);
            return "*".repeat(firstString.length());
        } else {
            return "*".repeat(firstString.length());
        }
    }

    @Override
    public void print(MergeInterface mergeInterface) {
        System.out.println(mergeInterface.printTokens(this.tokens));
    }
}
