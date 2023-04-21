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

    // TODO: why does this need the argument flagSensitive?
    private fun match(str: String, flagSensitive: Boolean): Node? {
        var tkAux: Node? = null

        tokens.forEach { tk ->
            if (!matchToken(tk, str)) {
                continue
            }
            if (tk.sensitive) {
                return tk
            }
            if (tk.sensitive == flagSensitive) {
                tkAux = tk
            }
        }

        return tkAux
    }

    fun hasToken(s: String): Boolean {
        // TODO
        return true
    }

    // Matches the string at the head of the input text list with the tokens in
    // the terminal node. If a match is found, removes the matched string from
    // the input list and returns the remaining strings. Otherwise, creates a
    // new token and adds it to the list of tokens.
    override fun parse(input: List<String>): String {
        if (input.isEmpty()) {
            return ""
        }

        var sensitive = false
        val firstString = input[0]

        if (firstString[0] == '<') {
            if (hasToken(firstString)) {
                firstString = firstString.substring(3, firstString.length - 4)
                sensitive = true
            }
        }

        val tk: Node = match(firstString, sensitive)
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

    override fun print() {
        tokens.forEach { token ->
            print(token.regex + " ")
        }
        println("")
    }
}
