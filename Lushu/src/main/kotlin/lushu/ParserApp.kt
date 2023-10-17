package lushu

import lushu.ContextGrammar.Grammar.DescendentParser
import lushu.Merger.Lattice.Node.MergerS
import lushu.ContextGrammar.Grammar.Grammar

fun main(args: Array<String>) {
    if (args.size < 1) {
        println(
            "Usage: cat <input-file> | <this-program> <merger-config-file>\n" +
                "\t<log-train-file> <print-statistics>",
        )
        return
    }
    val configFilePath = args[0]

    MergerS.load(configFilePath)
    val res = Grammar().doParserFromHTMLGenerator()
}
