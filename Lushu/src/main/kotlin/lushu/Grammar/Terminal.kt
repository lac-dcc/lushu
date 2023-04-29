package lushu.Grammar

import lushu.Merger.Merger.Token

// The Terminal class represents a terminal node in a data structure. It extends
// the Node class and provides methods to add tokens, match input, and print
// node information.
class Terminal(
    // A list of tokens for the terminal node.
    private var tokens: List<Token> = listOf()
) : Node {
    // fun addToken(s: String, sensitive: Boolean) {
    //     val intervalNodes = getNodeFactory().buildIntervalNodes(s, sensitive)

    //     if (tokens.isEmpty()) {
    //         tokens = merge(intervalNodes, intervalNodes)
    //     } else {
    //         val mergerResult = getMerger().merge(tokens, intervalNodes)
    //         if (mergerResult.size() == 1 && isTop(mergerResult)) {
    //             tokens += getMerger().merge(intervalNodes, intervalNodes)
    //         } else {
    //             tokens = mergerResult
    //         }
    //     }
    // }

    // private fun match(s: String): Node? {
    //     var token: Node? = null

    //     tokens.forEach { tk ->
    //         if (tk.match(s)) {
    //             if (tk.getSensitive()) {
    //                 return tk
    //             } else {
    //                 token = tk
    //             }
    //         }
    //     }

    //     return token
    // }

    // // TODO: why does this need the argument flagSensitive?
    // private fun match(str: String, flagSensitive: Boolean): Node? {
    //     var tkAux: Node? = null

    //     tokens.forEach { tk ->
    //         if (!matchToken(tk, str)) {
    //             continue
    //         }
    //         if (tk.sensitive) {
    //             return tk
    //         }
    //         if (tk.sensitive == flagSensitive) {
    //             tkAux = tk
    //         }
    //     }

    //     return tkAux
    // }

    // fun hasFlagSensitive(s: String): Pair<String, Boolean> {
    //     if (s[0] == '<') {
    //         val regex = Regex("<s>.*</s>")
    //         if (regex.matches(s)) {
    //             return Pair(s.substring(3, s.length - 4), true)
    //         }
    //         return Pair(s, false)
    //     }
    //     return Pair(s, false)
    // }

    // fun hasToken(s: String): Boolean {
    //     // TODO
    //     return true
    // }

    // Matches the string at the head of the input text list with the tokens in
    // the terminal node. If a match is found, removes the matched string from
    // the input list and returns the remaining strings. Otherwise, creates a
    // new token and adds it to the list of tokens.
    override fun match(input: List<String>): List<String> {
        return input
        // var(s, sensitive) = hasFlagSensitive(input.get(0))

        // val token: Node? = match(s)

        // if (token == null) {
        //     addToken(s, sensitive)
        // } else if (!token.getSensitive() && sensitive) {
        //     addToken(s, sensitive)
        // }

        // if (token.getSensitive() || sensitive) {
        //     return "*".repeat(s.length)
        // }
        // return s
    }

    override fun toString(): String {
        return "Terminal(tokens=$tokens)"
    }
}
