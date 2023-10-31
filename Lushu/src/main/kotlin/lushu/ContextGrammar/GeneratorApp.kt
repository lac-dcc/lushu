package lushu.ContextGrammar

import lushu.ContextGrammar.Grammar.CGenerator
import lushu.ContextGrammar.Grammar.HTMLGenerator

val HTML_OPT = 0
val C_OPT = 1
fun main(args: Array<String>) {
    if (args.size < 3) {
        println(
            "Usage: <this-program> <generator-type> <output-file> <number-of-tokens>",
        )
        return
    }

    val option: Int = args[0].toInt()
    val file: String = args[1]
    val numTokens: Int = args[2].toInt()
    if (option == HTML_OPT) {
        HTMLGenerator().startGenerator(numTokens, file)
    } else if (option == C_OPT) {
        CGenerator().startGenerator(numTokens, file)
    } else {
        println(
            "options:\nHTML = 0\nC = 1\n",
        )
        return
    }
}
