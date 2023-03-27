/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.list;

import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public class Terminal extends Node {

    private ArrayList<Token> tokens;

    public Terminal() {
        tokens = new ArrayList<>();
    }

    public void addToken(Token token) {
        this.tokens.add(token);
    }
    
    public ArrayList<Token> getTokens(){
        return this.tokens;
    }
    
    @Override
    ArrayList<String> match(ArrayList<String> input) {
        if(input.isEmpty())
            return input;
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
    public void print(){
        this.getTokens().forEach(token -> System.out.print(token.getRegex() + " "));
        System.out.println("");
    }
}
