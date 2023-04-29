package lushu.Grammar.Grammar

class Grammar {
    private var grammar = NonTerminal.new()
    private val tokenSeparator = " "

    fun consumeFromStdin() {
        var input = readLine()
        while (input == null || input.isEmpty()) {
            input = readLine()
        }
        grammar = NonTerminal.new(input.split(tokenSeparator)[0])
        while (input != null && !input.isEmpty()) {
            val words = input.split(tokenSeparator)
            if (words.isEmpty()) {
                continue
            }
            grammar.consume(words)
            input = readLine()
        }
    }

    // print pretty-prints the grammar
    fun print(): String {
        return grammar.print()
    }

    override fun toString(): String {
        return grammar.toString()
    }
}
