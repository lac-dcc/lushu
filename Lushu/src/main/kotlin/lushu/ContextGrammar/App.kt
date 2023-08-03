package lushu.ContextGrammar

import lushu.ContextGrammar.Grammar.Grammar

fun main(args: Array<String>) {
    var grammar: Grammar = Grammar()
    grammar.trainStdin()
    println("Log:")
    println(grammar.consumeStdin())
}
