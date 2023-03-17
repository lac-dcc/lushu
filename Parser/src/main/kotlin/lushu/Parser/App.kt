package lushu.ParSy

import java.io.File

fun main(args: Array<String>) {
    if (args.size < 1) {
        println(
            "Unable to execute Lushu parser without arguments.\n" +
                "Usage: <program> <input-file>"
        )
        return
    }
    val inputFile = args[0]
    val tokenDelimeter = " "

    // TODO
    println("Hello World")
}
