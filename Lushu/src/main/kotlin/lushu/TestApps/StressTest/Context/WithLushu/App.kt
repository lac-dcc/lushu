package lushu.TestApps.StressTest.Context.WithLushu

import lushu.ContextGrammar.Grammar.Grammar
import lushu.Merger.Lattice.Node.MergerS
import java.io.File
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    if (args.size < 4) {
        println(
            "Usage: <this-program> <merger-config-file>\n" +
                "\t<input-file> <patterns-file> <output-file>"
        )
        return
    }

    val configFilePath = args[0]
    val testFilePath = args[1]
    val patternsFilePath = args[2]
    val outputFilePath = args[3]

    MergerS.load(configFilePath)
    var grammar: Grammar = Grammar()

    if (!patternsFilePath.isNullOrBlank()) {
        var file = File(patternsFilePath)
        grammar.trainMap(file)
    }
    var time: Long = 0
    time = measureTimeMillis {
        grammar.testMap(testFilePath, outputFilePath)
    }
    val runtime = Runtime.getRuntime()
    val memory = (runtime.totalMemory() - runtime.freeMemory())

    System.err.println("$time")
    System.err.println("$memory")
}
