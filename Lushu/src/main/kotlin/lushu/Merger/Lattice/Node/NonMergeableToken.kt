package lushu.Merger.Lattice.Node

class NonMergeableToken(
    pattern: String = ""
) : Token {

    override var tokens: List<Node> = listOf()

    override fun setToken(pattern: String) {
        this.tokens = MergerS.merger().tokensFromString(pattern)
    }

    init {
        setToken(pattern)
    }

    override fun match(string: String): Boolean {
        val tokens = MergerS.merger().tokensFromString(string)
        return match(tokens)
    }

    override fun match(tokens: List<Node>): Boolean {
        return this.tokens == tokens
    }

    override fun toString(): String {
        return "${this.tokens}"
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is NonMergeableToken -> this.tokens == other.tokens
        else -> false
    }
}
