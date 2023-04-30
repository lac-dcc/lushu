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
        logger.debug("Terminal matching $word")
        var cleanWord = word
        val match = captureSensitivity(word)
        if (match != "") {
            cleanWord = match
        }
        val res = MergerS.merger().merge(tokens, cleanWord)
        if (res.success) {
            logger.debug("Word $cleanWord merged! New tokens: ${res.tokens}")
            tokens = res.tokens
            val obfuscate = tokens[0].sensitive
            if (obfuscate) {
                return Result(true, constantMask)
            } else {
                return Result(true, cleanWord)
            }
        }
        logger.debug("Word $cleanWord is not mergeable with $tokens")
        return Result(false, cleanWord)
    }

    // print pretty-prints the NonTerminal tree
    fun print(): String {
        return NodePrinter.print(tokens)
    }

    override fun toString(): String {
        return "Terminal(tokens=$tokens)"
    }

    companion object {
        private val sensitiveRegex = Regex("^<s>(.+)</s>$")

        val constantMask = "*****"

        fun new(s: String = ""): Terminal {
            val match = captureSensitivity(s)
            if (match == "") {
                return Terminal(MergerS.merger().tokensFromString(s, false))
            } else {
                return Terminal(MergerS.merger().tokensFromString(match, true))
            }
        }

        fun captureSensitivity(word: String): String {
            val matches = sensitiveRegex.findAll(word)
            if (matches.count() < 1) {
                return ""
            }
            return matches.single().groupValues[1]
        }
    }
}
