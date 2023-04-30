package lushu.Grammar.Grammar

import org.slf4j.LoggerFactory

class NonTerminal(
    val id: Int = 0,
    private var terminals: List<Terminal>,
    private var next: NonTerminal? = null
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun consume(input: List<String>): List<String> {
        if (input.isEmpty()) {
            return input
        }
        var res = Terminal.Result()
        val word = input[0]
        terminals.forEach {
            res = it.consume(word)
            if (res.consumed) {
                return@forEach
            }
        }
        if (!res.consumed) {
            // No terminals were able to consume a word. So we must add a
            // terminal with a new token that recognizes that word.
            terminals += Terminal.new(word)
        }
        if (input.size == 1) {
            // Must have been consumed by the terminals already; return.
            return listOf(res.obfuscated)
        }

        // Process the next word
        val consumed = input.drop(1)
        val n = next
        if (n == null) {
            next = new(consumed[0], id + 1)
        }
        return listOf(res.obfuscated) + next!!.consume(consumed)
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
            return NonTerminal(id, listOf(Terminal.new(s)), null)
        }
    }
}