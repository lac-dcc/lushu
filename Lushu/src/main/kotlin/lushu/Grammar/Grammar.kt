package lushu.Grammar

class Grammar(
    // The root(non-terminal node) of the grammar.
    private var grammar: NonTerminal = NonTerminal(Terminal(), null)
) {
    fun parse(text: String) {
        var input = text.split(" ")
        while (!input.isEmpty()) {
            input = grammar.parse(input)
        }
    }

    // Prints the regular expressions for each token present in the grammar.
    fun print() {
        grammar.print()
    }
}
