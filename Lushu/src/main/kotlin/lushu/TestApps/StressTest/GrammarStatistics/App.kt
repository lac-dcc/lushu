package lushu.TestApps.StressTest.GrammarStatistics

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS
import lushu.Interceptor.PrintStream.LushuPrintStream
import lushu.LogGenerator.LogGenerator
import java.io.PrintStream

fun main(args: Array<String>) {
    if (args.size < 4) {
        println(
            "Usage: <this-program> <merger-config-file> <log-train-file>\n" +
                "\t<log-generator-base-dir> <num-logs> [<logs-file>]"
        )
        return
    }
    val configFilePath = args[0]
    val trainFile = args[1]
    val logGeneratorBaseDir = args[2]
    val numLogs = args[3].toInt()

    MergerS.load(configFilePath)

    val grammar = Grammar.fromTrainFile(trainFile)
    System.setOut(LushuPrintStream(System.out, grammar))
    val lg = LogGenerator(logGeneratorBaseDir)
    lg.run(numLogs)
    System.setOut(PrintStream(System.out))

    System.err.print("${grammar.statistics()}")
}
