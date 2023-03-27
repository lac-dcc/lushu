/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.list;

/**
 *
 * @author vitor
 */
public class Main {
    public static void main(String[] args){
        TxtFileLog tfl = new TxtFileLog("./text_files/test.txt", "./text_files/out.txt");
        String text = tfl.read();
        
        ListManipulator tm = new ListManipulator();
        while(text != null){
            tm.build(text);
            text = tfl.read();
        }
        tm.print();
    }
}
