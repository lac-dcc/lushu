package lushu.Grammar

class Grammar {
    private val grammar: NonTerminal = NonTerminal(Terminal(), null)
    private val tokenSeparator = " "

    fun consumeFromStdin() {
        val input = readLine()
        while (input != null && !input.isEmpty()) {
            val words = input.split(tokenSeparator)
            if (words.isEmpty()) {
                continue
            }
            val notConsumed = grammar.consume(words)

            // This is an unexpected situation which indicated the program is
            // not behaving as it should. So, as an assertion, we expect the
            // program to stop here if that is the case.
            if (!notConsumed.isEmpty()) {
                throw Exception("Grammar was unable to consume all the tokens!")
            }
        }
    }

    override fun toString(): String {
        return grammar.toString()
    }
}
