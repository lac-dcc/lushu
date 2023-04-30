package lushu.Grammar.Grammar

import lushu.Merger.Lattice.NodePrinter
import lushu.Merger.Merger.Token
import org.slf4j.LoggerFactory

class Terminal(
    private var tokens: List<Token>
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    data class Result(
        val consumed: Boolean = false,
        val obfuscated: String = ""
    )

    // Consumes the string at the head of the input text list with the tokens in
    // the terminal node.
    fun consume(word: String): Result {
        val res = MergerS.merger().merge(tokens, word)
        if (res.success) {
            logger.debug("Word $word merged! New tokens: ${res.tokens}")
            tokens = res.tokens
            val obfuscate = tokens[0].sensitive
            if (obfuscate) {
                return Result(true, constantMask)
            } else {
                return Result(true, word)
            }
        }
        logger.debug("Word $word is not mergeable with $tokens")
        return Result(false, word)
    }

    // print pretty-prints the NonTerminal tree
    fun print(): String {
        return NodePrinter.print(tokens)
    }

    override fun toString(): String {
        return "Terminal(tokens=$tokens)"
    }

    companion object {
        private val sensitiveRegex = Regex("^<(\\p{Alnum})+>(.+)</\\1>$")

        val constantMask = "*****"

        fun new(s: String = ""): Terminal {
            val match = captureSensitivity(s)
            if (match == "") {
                return Terminal(MergerS.merger().tokensFromString(s, false))
            } else {
                return Terminal(MergerS.merger().tokensFromString(match, true))
            }
        }

        private fun captureSensitivity(word: String): String {
            val matches = sensitiveRegex.findAll(word)
            if (matches.count() < 1) {
                return ""
            }
            val groupValues = matches.single().groupValues
            println("Group values: $groupValues")
            return groupValues[2]
        }
    }
}
