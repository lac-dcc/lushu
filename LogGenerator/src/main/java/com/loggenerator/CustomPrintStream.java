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

