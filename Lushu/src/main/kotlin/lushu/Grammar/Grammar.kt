package lushu.Grammar

class Grammar(
    // The root(non-terminal node) of the grammar.
    private var grammar: Node = NonTerminal(Terminal(), null),
) {
    // Parsers the input string using the dynamic grammar
    fun parse(text: String): String {
        return grammar.match(text.split(" "))
    }

    // Prints the regular expressions for each token present in the grammar.
    override fun toString(): String {
        return grammar.toString()
    }
}
