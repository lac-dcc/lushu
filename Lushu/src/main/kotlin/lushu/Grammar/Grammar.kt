package lushu.Grammar

// The Grammar class represents a regular grammar in a data structure. It
// provides methods to build the grammar from a text string and print its
// tokens.
class Grammar(
    // The root(non-terminal node) of the grammar.
    private var grammar: NonTerminal = NonTerminal(Terminal(), null)
) {
    // Builds the grammar data structure from a text string.Splits the text
    // string into an List of strings and matches each string with the nodes in
    // the data structure.
    fun build(text: String) {
        var input = text.split(" ")
        while (!input.isEmpty()) {
            input = grammar.match(input)
        }
    }

    // Prints the regular expressions for each token present in the grammar.
    fun print() {
        grammar.print()
    }
}
