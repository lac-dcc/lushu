package lushu.TestApps.StressTest.Time.WithLushu

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS
import lushu.Grammar.Grammar.NonTerminal
import lushu.Interceptor.PrintStream.LushuPrintStream
import lushu.LogGenerator.LogGenerator
import java.io.PrintStream
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    if (args.size < 4) {
        println(
            "Usage: <this-program> <merger-config-file> <log-train-file>\n" +
                "\t<log-generator-base-dir> <num-logs>"
        )
        return
    }
    val configFilePath = args[0]
    val trainFile = args[1]
    val logGeneratorBaseDir = args[2]
    val numLogs = args[3].toInt()

    MergerS.load(configFilePath)
    val lg = LogGenerator(logGeneratorBaseDir)

    val grammar = Grammar.fromTrainFile(trainFile)
    System.setOut(LushuPrintStream(System.out, grammar))
    val time = measureTimeMillis { lg.run(numLogs) }
    val runtime = Runtime.getRuntime()
    runtime.gc()
    val memory = runtime.totalMemory() - runtime.freeMemory()
    System.setOut(PrintStream(System.out))
    
    System.err.println("$time")
    System.err.println("$memory")
}
