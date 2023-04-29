package lushu.Grammar

fun main(args: Array<String>) {
    if (args.size < 1) {
        println(
            "Usage: cat <input-file> | <this-program> <merger-config-file>"
        )
        return
    }
    val configFilePath = args[0]
    val grammar = Grammar(configFilePath)
    grammar.matchFromStdin()
    print(grammar)
}
