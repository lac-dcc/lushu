package lushu.Grammar

import java.io.File

fun main(args: Array<String>) {
    val grammar = Grammar()
    File(args[0]).forEachLine { grammar.parse(it) }
    println(grammar)
}
