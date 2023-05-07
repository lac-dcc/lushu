package lushu.TestApps.StressTest.WoutLushu

import lushu.LogGenerator.LogGenerator
import java.io.File
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    if (args.size < 2) {
        println(
            "Usage: <this-program> <log-generator-base-dir> <num-logs>\n" +
                "\t[<logs-file>]"
        )
        return
    }
    val logGeneratorBaseDir = args[0]
    val numLogs = args[1].toInt()

    // logsFile is optional. It can be used instead of the log generator if
    // needed.
    var logsFile: String = ""
    if (args.size > 2) {
        logsFile = args[2]
    }

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

    System.err.println("$time")
    System.err.println("$memory")
}
