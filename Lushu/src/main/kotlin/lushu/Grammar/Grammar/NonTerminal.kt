package lushu.Grammar.Grammar

import org.slf4j.LoggerFactory

class NonTerminal(
    val id: Int = 0,
    private var terminals: List<Terminal>,
    private var next: NonTerminal? = null
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    class Result(
        val results: List<Terminal.Result>
    ) {
        override fun toString(): String {
            return results.map { it.word }.joinToString(" ")
        }
    }

    fun consumeRecursive(input: List<String>): List<Terminal.Result> {
        if (input.isEmpty()) {
            return listOf<Terminal.Result>()
        }
        var res = Terminal.Result()
        val word = input[0]
        for (i in 0 until terminals.size) {
            if (res.consumed) {
                numTerminalConsumes++
                break
            }
            res = terminals[i].consume(word)
        }
        if (!res.consumed) {
            // No terminals were able to consume a word. So we must add a
            // terminal with a new token that recognizes that word.
            terminals += Terminal.new(word)
        }
        if (input.size == 1) {
            // Must have been consumed by the terminals already; return.
            return listOf(res)
        }

        // Process the next word
        val consumed = input.drop(1)
        val n = next
        if (n == null) {
            next = new(consumed[0], id + 1)
        }
        return listOf(res) + next!!.consumeRecursive(consumed)
    }

    fun consume(input: List<String>): Result {
        val results = consumeRecursive(input)
        return Result(results)
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

    fun numNonTerminals(): Int {
        return 1 + (if (next != null) next!!.numNonTerminals() else 0)
    }

    fun numTerminals(): Int {
        var num = terminals.size
        if (next != null) {
            num += next!!.numTerminals()
        }
        return num
    }

    fun numTokens(): Int {
        var num = 0
        terminals.forEach { num += it.numTokens() }
        return num + (if (next != null) next!!.numTokens() else 0)
    }

    fun isLast(): Boolean {
        return next == null
    }

    override fun toString(): String {
        return "NonTerminal(terminals=$terminals next=$next)"
    }

    companion object {
        var numTerminalConsumes = 0

        fun new(s: String = "", id: Int = 0): NonTerminal {
            return NonTerminal(id, listOf(Terminal.new(s)), null)
        }
    }
}
