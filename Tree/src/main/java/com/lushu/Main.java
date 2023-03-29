/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lushu;

import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public class Main {
    public static void main(String[] args){
        TxtFileLog tfl = new TxtFileLog("./text_files/test.txt", "./text_files/out.txt");
        String text = tfl.read();
        LushuManipulator ltm = new LushuManipulator();
        while(text != null){
            ltm.build(text);
            text = tfl.read();
        }
        ltm.printTree();
        
        ltm.mergeTreeChildren(null , "isso", "1");
        ltm.printTree();
        
        System.out.println("Remove");
        ArrayList al = new ArrayList<Integer>();
        al.add(0);
        al.add(2);
        ltm.removeByIndex(null, al);
        ltm.printTree();
        
        
        ltm.mergeTreeChildren(null , "esse", "1");
        ltm.printTree();
        
        ltm.mergeTree();
        ltm.printTree();
    }
}
