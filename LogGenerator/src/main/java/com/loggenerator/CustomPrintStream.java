package com.loggenerator;

import java.io.OutputStream;
import java.io.PrintStream;
import com.grammar.Grammar;

public class CustomPrintStream extends PrintStream  {
    Grammar g;
    private final String Preffix = "> ";

    public CustomPrintStream(OutputStream out) {
        super(out);
        g = new Grammar();
    }
    
    @Override
    public void print(String s)  {

        /*
         * Modify this method! Here you should do:
         * 1) Check if a given grammar can parse string 's' (you can load the grammar file when the agent is initialized and update that as needed using Antlr and the grammar generation algorithm).
         * 1.1) If it can, generate the AST to figure out where the SQL query is.
         * 1.2) If it cannot, generate a new grammar that contains string 's' as a new input example.
         * 2) Parse the SQL query to get it's AST (you can do that with ANTLR, they have a SQL grammar on their github)
         * 3) Compare the SQL AST you have with the AST the user provided to figure out where sensitive data is.
         * 4) Redact what is sensite. 
         */

        super.print(Preffix);
        s = g.conformSensitiveGrammar(s);
        super.print(s);
    }

    @Override
    public void print(boolean b)  {
        this.print(String.valueOf(b));
    }

    @Override
    public void print(char c)  {
        this.print(String.valueOf(c));
    }

    @Override
    public void print(char[] s)  {
        this.print(String.valueOf(s));
    }

    @Override
    public void print(double d)  {
        this.print(String.valueOf(d));
    }

    @Override
    public void print(float f)  {
        this.print(String.valueOf(f));
    }

    @Override
    public void print(int i)  {
        this.print(String.valueOf(i));
    }

    @Override
    public void print(long l)  {
        this.print(String.valueOf(l));
    }

    @Override
    public void print(Object obj)  {
        this.print(String.valueOf(obj));
    }
}

