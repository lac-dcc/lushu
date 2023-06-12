package lushu.ContextGrammar
import lushu.ContextGrammar.Grammar.Grammar
fun main (args: Array<String>){
    var grammar = Grammar.fromStdinTrain()
    grammar = Grammar.fromStdin()
}