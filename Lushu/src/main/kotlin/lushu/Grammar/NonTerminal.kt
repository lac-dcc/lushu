package lushu.Grammar.Grammar

// The NonTerminal class represents a non-terminal node in a data structure. It
// extends the Node class and provides methods to match input and print node
// information.
class NonTerminal(
    // The first node in the non-terminal. This will be a terminal node.
    private var first: Node,
    // The second node in the non-terminal. This will be a non-terminal node.
    private var second: Node? = NonTerminal(Terminal(), null)
) : Node {
    // Matches the string at the head of the input text list with the tokens in
    // the first node. If a match is found, returns the tail of the input list
    // (the remaining strings after the matched string). Otherwise, creates a
    // new terminal node and tries to match again. If there is a second node,
    // passes the tail of the input list to it for further matching.
    override fun match(input: List<String>): List<String> {
        val tail = first.match(input)
        if (tail.isEmpty()) {
            return tail
        }

        val sec = second
        if (sec == null) {
            return listOf<String>()
        } else {
            return sec.match(tail)
        }
    }

    // Prints the regular expressions for each token present in the terminal
    // node and call to the next non-terminal node.
    override fun print() {
        first.print()
        val sec = second
        if (sec != null) {
            sec.print()
        }
    }
}
