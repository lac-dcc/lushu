package com.grammar;

/**
 *
 * @author vitor
 */
public class Main {
    public static void main(String[] args){
        TxtFileLog tfl = new TxtFileLog(args[0],args[1]);
        String text = tfl.read();
        
        Grammar tm = new Grammar();
        while(text != null){
            System.out.println(tm.conformSensitiveGrammar(text));
            text = tfl.read();
        }
        System.out.println("\nTOKENS:");
        tm.print();
    }
}
