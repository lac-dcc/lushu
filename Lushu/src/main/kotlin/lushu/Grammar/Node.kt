package lushu.Grammar

interface Node {
    // Matches the string at the head of the input text list with the tokens in
    // the node. If a match is found, returns the tail of the input list (the
    // remaining strings after the matched string).
    fun match(input: List<String>): List<String>

    // Prints the regular expressions for each token present in the node.
    fun print()
}
