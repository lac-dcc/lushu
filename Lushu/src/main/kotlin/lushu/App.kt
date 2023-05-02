package lushu

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS

fun main(args: Array<String>) {
    if (args.size < 2) {
        println(
            "Usage: cat <input-file> | <this-program> <merger-config-file> <log-train-file>"
        )
        return
    }
    val configFilePath = args[0]
    val trainFile = args[1]
    MergerS.load(configFilePath)
    val grammar = Grammar.fromTrainFile(trainFile)
    val obfuscatedLines = grammar.consumeStdin()
    print(obfuscatedLines)
}
