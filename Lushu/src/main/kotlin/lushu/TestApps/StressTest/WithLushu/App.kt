package lushu.TestApps.StressTest.WithLushu

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS
import lushu.Interceptor.PrintStream.LushuPrintStream
import lushu.LogGenerator.LogGenerator
import java.io.File
import java.io.PrintStream
import kotlin.system.measureTimeMillis

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

    // logsFile is optional. It can be used instead of the log generator if
    // needed.
    var logsFile: String = ""
    if (args.size > 4) {
        logsFile = args[4]
    }

    MergerS.load(configFilePath)

    val grammar = Grammar.fromTrainFile(trainFile)
    System.setOut(LushuPrintStream(System.out, grammar))
    var time: Long
    if (logsFile == "") {
        val lg = LogGenerator(logGeneratorBaseDir)
        time = measureTimeMillis { lg.run(numLogs) }
    } else {
        val logs = File(logsFile).readLines()
        time = measureTimeMillis {
            logs.forEach {
                println(it)
            }
        }
    }
    val runtime = Runtime.getRuntime()
    runtime.gc()
    val memory = runtime.totalMemory() - runtime.freeMemory()
    System.setOut(PrintStream(System.out))

    System.err.println("$time")
    System.err.println("$memory")
}
