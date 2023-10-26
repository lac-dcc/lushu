package lushu.TestApps.StressTest.Context.WithLushu

import lushu.ContextGrammar.Grammar.Grammar
import lushu.Merger.Lattice.Node.MergerS
import java.io.File
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    if (args.size < 3) {
        println(
            "Usage: cat <input-file> | <this-program> <merger-config-file>\n" +
                "\t<html-file> <pattern-file-train> <emails-result-file>",
        )
        return
    }

    val configFilePath = args[0]
    val htmlfiletest = args[1]
    val filePatterns = args[2]
    val emailsFile = args[3]

    MergerS.load(configFilePath)
    var grammar: Grammar = Grammar()

    if (!filePatterns.isNullOrBlank()) {
        var file = File(filePatterns)
        grammar.trainMap(file)
    }
    var time: Long = 0

    var emails: List<String>
    time = measureTimeMillis {
        emails = grammar.testMap(htmlfiletest)
    }

    val runtime = Runtime.getRuntime()
    runtime.gc()
    val memory = runtime.totalMemory() - runtime.freeMemory()

    val outfile = File(emailsFile).bufferedWriter()
    if (!emailsFile.isNullOrBlank()) {
        for (email in emails) {
            outfile.write(email)
            outfile.newLine()
        }
        outfile.close()
    }

    System.err.println("$time")
    System.err.println("$memory")
}
