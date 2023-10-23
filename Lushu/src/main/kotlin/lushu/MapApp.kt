package lushu

import lushu.ContextGrammar.Grammar.Grammar
import lushu.Merger.Lattice.Node.MergerS

fun main(args: Array<String>) {
    if (args.size < 1) {
        println(
            "Usage: cat <input-file> | <this-program> <merger-config-file>\n" +
                "\t<log-train-file> <print-statistics>",
        )
        return
    }
    val configFilePath = args[0]
    val testFile = args[1]

    MergerS.load(configFilePath)
    val resHTML = Grammar().testMapHTML()
    //val resFile = Grammar().testMapFile(testFile)
}
