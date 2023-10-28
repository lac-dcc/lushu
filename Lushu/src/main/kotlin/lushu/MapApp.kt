package lushu

import lushu.ContextGrammar.Grammar.Grammar
import lushu.ContextGrammar.Grammar.HTMLGenerator
import lushu.Merger.Lattice.Node.MergerS
import java.io.File

fun main(args: Array<String>) {
    val file: String = args[0]
    val numTokens: Int = args[1].toInt()
    HTMLGenerator().startGenerator(numTokens, file)
}
