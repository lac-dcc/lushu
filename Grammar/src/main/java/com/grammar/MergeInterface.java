package com.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lushu.Merger.Config.Config;
import lushu.Merger.Lattice.MergerLattice;
import lushu.Merger.Lattice.NodePrinter;
import lushu.Merger.Lattice.NodeFactory;
import lushu.Merger.Merger.Merger;
import lushu.Merger.Lattice.Node.Node;
/**
 *
 * @author vitor
 */
public class MergeInterface {
    
    private Merger merger;
    private NodeFactory nf;
    
    public MergeInterface(){
        ArrayList<String> args; 
        args = new ArrayList<>();
        args.add("./text_files/config.yaml");
        var config = Config.Companion.fromCLIArgs(args);
        nf = config.getNodeFactory();
        merger = new Merger(new MergerLattice(nf));
    }
    public NodeFactory getNodeFactory(){
        return this.nf;
    }
    public Merger getMerger(){
        return this.merger;
    }
    public String printTokens(List<? extends Node> TokenList){
        return NodePrinter.Companion.print(TokenList);
    }
    public String getTokenRegex(Node Token){
        return NodePrinter.Companion.print(Token);
    }
    public boolean matchToken(Node Token, String str){
        Pattern pattern = Pattern.compile(getTokenRegex(Token));
        return pattern.matcher(str).matches();
    }
    public boolean isTop(Node Token){
        return this.nf.Companion.isTop(Token);
    }
}
