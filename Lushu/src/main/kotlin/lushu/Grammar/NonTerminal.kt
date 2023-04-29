package lushu.Grammar

import org.slf4j.LoggerFactory

class NonTerminal(
    private var terminals: List<Terminal>,
    private var next: NonTerminal? = null
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun consume(input: List<String>) {
        var consumed = listOf<String>()
        terminals.forEach {
            consumed = it.consume(input)
            if (consumed.size != input.size) {
                return@forEach
            }
        }
        if (consumed.isEmpty()) {
            return
        }
        val n = next
        if (n == null) {
            next = new(consumed[0])
        }
        next!!.consume(consumed)
    }

    // print pretty-prints the NonTerminal tree
    fun print(): String {
        var s = ""
        terminals.forEach {
            s += it.print()
        }
        if (next != null) {
            s += next.toString()
        }
        return s
    }

    override fun toString(): String {
        return "NonTerminal(terminals=$terminals next=$next)"
    }

    companion object {
        fun new(s: String): NonTerminal {
            return NonTerminal(
                listOf<Terminal>(
                    Terminal(MergerS.merger().tokensFromString(s))
                ),
                null
            )
        }
    }
}
