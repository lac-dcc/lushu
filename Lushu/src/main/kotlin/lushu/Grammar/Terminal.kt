package lushu.Grammar

import lushu.Merger.Lattice.NodePrinter
import lushu.Merger.Merger.Token
import org.slf4j.LoggerFactory

class Terminal(
    private var tokens: List<Token>
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    // Consumes the string at the head of the input text list with the tokens in
    // the terminal node.
    fun consume(input: List<String>): List<String> {
        // Assume input has at least size one
        val word = input[0]
        val res = MergerS.merger().merge(tokens, word)
        if (res.success) {
            logger.debug("Input entry $word merged! New tokens: ${res.tokens}")
            tokens = res.tokens
            return input.drop(1)
        }
        logger.debug("Input entry $word is not mergeable with $tokens")
        return input
    }

    // print pretty-prints the NonTerminal tree
    fun print(): String {
        return NodePrinter.print(tokens)
    }

    override fun toString(): String {
        return "Terminal(tokens=$tokens)"
    }
}
