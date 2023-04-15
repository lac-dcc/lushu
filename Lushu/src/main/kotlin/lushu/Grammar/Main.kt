package lushu.Grammar.Grammar

import java.io.File

fun main(args: Array<String>) {
    val grammar = Grammar()
    File(args[0]).forEachLine { grammar.build(it) }
    grammar.print()
}
