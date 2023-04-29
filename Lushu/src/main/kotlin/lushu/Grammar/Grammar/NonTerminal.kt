package lushu.Grammar.Grammar

import org.slf4j.LoggerFactory

class NonTerminal(
    val id: Int = 0,
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
            next = new(consumed[0], id + 1)
        }
        next!!.consume(consumed)
    }

    // print pretty-prints the NonTerminal tree
    fun print(): String {
        var s = "R$id :: "
        terminals.forEach {
            s += it.print()
            s += " | "
        }
        val n = next
        if (n != null) {
            s += "R${n.id}\n"
            s += n.print()
        } else {
            s = s.removeSuffix(" | ")
            s += "\n"
        }
        return s
    }

    override fun toString(): String {
        return "NonTerminal(terminals=$terminals next=$next)"
    }

    companion object {
        fun new(s: String = "", id: Int = 0): NonTerminal {
            return NonTerminal(
                id,
                listOf<Terminal>(
                    Terminal(MergerS.merger().tokensFromString(s))
                ),
                null
            )
        }
    }
}
