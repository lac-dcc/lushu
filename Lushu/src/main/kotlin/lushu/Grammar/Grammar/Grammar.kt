package lushu.Grammar.Grammar

class Grammar {
    private var grammar = NonTerminal.new()
    private val tokenSeparator = " "

    fun consume(words: List<String>): String {
        val consumedWords = grammar.consume(words)
        return consumedWords.joinToString(tokenSeparator)
    }

    fun consume(s: String): String {
        return consume(s.split(tokenSeparator))
    }

    // consumeFromStdin starts a new grammar from scratch. It reads every line
    // received from stdin, consuming every line.
    //
    // This is not very useful for interception, since usually the intercepted
    // strings will come the program itself inside the JVM.
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
