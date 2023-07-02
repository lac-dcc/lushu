package lushu

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS
import lushu.Interceptor.Interceptor

fun main(args: Array<String>) {
    if (args.size < 2) {
        println(
            "Usage: cat <input-file> | <this-program> <merger-config-file>\n" +
                "\t<log-train-file> <print-statistics>"
        )
        return
    }
    val configFilePath = args[0]
    val trainFile = args[1]
    var printStatistics = false
    if (args.size > 2) {
        printStatistics = args[2].toBoolean()
    }

    MergerS.load(configFilePath)
    val grammar = Grammar.fromTrainFile(trainFile)
    val res = grammar.consumeStdin()

    val interceptor = Interceptor(System.out, grammar)
    System.err.println("Created interceptor")

    interceptor.intercept {
        print(res)
    }

    if (printStatistics) {
        System.err.print("${grammar.statistics()}")
    }
}
