package lushu.Lexer

import lushu.Lexer.Config.Config
import lushu.Lexer.Lexer.Lexer

fun main(args: Array<String>) {
    if (args.size < 1) {
        println(
            "Usage: echo 'word1 word2 ...' | ./lushu <base-nodes-file> <disjoint-nodes-file>"
        )
        return
    }
    val config = Config.fromCLIArgs(args.toList())
    // TODO: read words from stdin
    val words = listOf<String>("word1", "word2")
    Lexer(config).run(words)
}
