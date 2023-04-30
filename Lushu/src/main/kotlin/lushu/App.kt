package lushu

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS

fun main(args: Array<String>) {
    if (args.size < 1) {
        println(
            "Usage: cat <input-file> | <this-program> <merger-config-file>"
        )
        return
    }
    val configFilePath = args[0]
    MergerS.load(configFilePath)
    val grammar = Grammar.fromStdin()
    print(grammar.print())
}
