/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.list;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author vitor
 */
public class ListManipulator {
    
    private NonTerminal tree;
    
    public ListManipulator(){
        tree = new NonTerminal(new Terminal(), null);
    }
    
    public void build(String text){
        Node node = tree;
        
        ArrayList<String> input = new ArrayList<>();
        
        input.addAll(Arrays.asList(text.split(" ")));
        
        while(!input.isEmpty()){
            input = node.match(input);
        }
    }
    
    public void print(){
        tree.print();
    }
}
