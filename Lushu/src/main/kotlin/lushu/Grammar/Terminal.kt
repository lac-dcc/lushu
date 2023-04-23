package lushu.Grammar

// TODO: use Merger token -aholmquist 2023-04-15
//
// import lushu.Merger.Merger.Token

// The Terminal class represents a terminal node in a data structure. It extends
// the Node class and provides methods to add tokens, match input, and print
// node information.
class Terminal(
    // A list of tokens for the terminal node.
    private var tokens: List<Token> = listOf<Token>()
) : Node {
    fun addToken(token: Token) {
        tokens += token
    }

    // Matches the string at the head of the input text list with the tokens in
    // the terminal node. If a match is found, removes the matched string from
    // the input list and returns the remaining strings. Otherwise, creates a
    // new token and adds it to the list of tokens.
    override fun match(input: List<String>): List<String> {
        if (input.isEmpty()) {
            return input
        }
        val first_string = input[0]
        tokens.forEach { tk ->
            if (tk.match(first_string)) {
                input.drop(1)
                return input
            }
        }

        tokens += Token(first_string)
        input.drop(1)

        return input
    }

    override fun toString(): String {
        var s = ""
        tokens.forEach { token ->
            s += token.regex
            s += " "
        }
        s += "\n"
        return s
    }
}
