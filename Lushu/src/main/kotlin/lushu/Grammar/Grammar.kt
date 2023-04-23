package lushu.Grammar

class Grammar(
    // The root(non-terminal node) of the grammar.
    private var grammar: Node = NonTerminal(Terminal(), null),
    // The merger of the grammar
    private var mergerInterface: MergerInterface = MergerInterface()
) {
    // Parsers the input string using the dynamic grammar
    fun parse(text: String): String {
        var input = text.split(" ")
        var output = ""
        while (!input.isEmpty()) {
            output += grammar.parse(input)
        }
        return output
    }

    // Prints the regular expressions for each token present in the grammar.
    fun print() {
        grammar.print()
    }
}
