package lushu.TestApps.StressTest.Context.WithLushu

import lushu.ContextGrammar.Grammar.Grammar
import lushu.Merger.Lattice.Node.MergerS
import java.io.File

fun main(args: Array<String>) {
    if (args.size < 4) {
        println(
            "Usage: <this-program> <merger-config-file>\n" +
                "\t<input-file> <patterns-file> <output-file>",
        )
        return
    }

    val configFilePath = args[0]
    val testFilePath = args[1]
    val patternsFilePath = args[2]
    val outputFilePath = args[3]

    MergerS.load(configFilePath)
    var grammar: Grammar = Grammar()
    grammar.trainMap(File(patternsFilePath))
    grammar.testMap(testFilePath, outputFilePath)
}
