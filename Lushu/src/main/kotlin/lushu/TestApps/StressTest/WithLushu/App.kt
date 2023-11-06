package lushu.TestApps.StressTest.WithLushu

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS
import lushu.Interceptor.Interceptor
import lushu.LogGenerator.LogGenerator
import java.io.File
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
    val interceptor = Interceptor(System.out, grammar)
    var time: Long = 0
    if (logsFile == "") {
        val lg = LogGenerator(logGeneratorBaseDir)
        time = measureTimeMillis {
            interceptor.intercept {
                lg.run(numLogs)
            }
        }
    } else {
        val logs = File(logsFile).readLines()
        time = measureTimeMillis {
            interceptor.intercept {
                logs.forEach {
                    println(it)
                }
            }
        }
    }

    val runtime = Runtime.getRuntime()
    runtime.gc()
    val memory = (runtime.totalMemory() - runtime.freeMemory())

    System.err.println("$time")
    System.err.println("$memory")
}
