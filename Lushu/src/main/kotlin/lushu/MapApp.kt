package lushu

import lushu.ContextGrammar.Grammar.Grammar
import lushu.ContextGrammar.Grammar.HTMLGenerator
import lushu.Merger.Lattice.Node.MergerS

fun main(args: Array<String>) {
    if (args.size < 1) {
        println(
            "Usage: cat <input-file> | <this-program> <merger-config-file>\n" +
                "\t<pattern-file-train> <html-file>",
        )
        return
    }

    val configFilePath = args[0]
    val filePatterns = args[1]
    val htmlfiletest = args[2]

    MergerS.load(configFilePath)
    val resFile = Grammar().testMap(filePatterns, htmlfiletest)
}
