package lushu.TestApps.StressTest.WoutLushu

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS
import lushu.Grammar.Grammar.NonTerminal
import lushu.Interceptor.PrintStream.LushuPrintStream
import lushu.LogGenerator.LogGenerator
import java.io.PrintStream
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    if (args.size < 3) {
        println(
            "Usage: <this-program> <log-generator-base-dir> <num-logs>"
        )
        return
    }
    val logGeneratorBaseDir = args[0]
    val numLogs = args[1].toInt()

    val lg = LogGenerator(logGeneratorBaseDir)

    val time = measureTimeMillis { lg.run(numLogs) }
    val runtime = Runtime.getRuntime()
    runtime.gc()
    val memory = runtime.totalMemory() - runtime.freeMemory()

    System.err.println("$time")
    System.err.println("$memory")
}
