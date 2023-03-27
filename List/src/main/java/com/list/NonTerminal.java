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
public class NonTerminal extends Node {

    final private Node first;
    private Node second;

    public NonTerminal(Node first, Node second) {
        this.first = first;
        this.second = second;
    }

    @Override
    ArrayList<String> match(ArrayList<String> input) {
        ArrayList<String> tail = first.match(input);
        
        if(tail.isEmpty()){
            return tail;
        }
        if(second == null){
            second = new NonTerminal(new Terminal(), null);
        }
        
        return second.match(tail);
    }
    
    public void print(){
        first.print();
        if(second != null)
            second.print();
    }
}
