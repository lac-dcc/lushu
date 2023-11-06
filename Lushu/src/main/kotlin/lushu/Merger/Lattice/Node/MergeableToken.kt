package lushu.Merger.Lattice.Node

class MergeableToken(
    pattern: String
) : Token {
    override var tokens: List<Node> = listOf()

    private val merger = MergerS.merger()

    init {
        setToken(pattern)
    }

    override fun setToken(pattern: String) {
        this.tokens = merger.tokensFromString(pattern)
    }

    override fun match(string: String): Boolean {
        val tokens = merger.tokensFromString(string)
        return match(tokens)
    }

    override fun match(tokens: List<Node>): Boolean {
        val res = merger.merge(this.tokens, tokens)
        if (res.success) {
            this.tokens = res.tokens
        }
        return res.success
    }

    override fun toString(): String {
        return "${this.tokens}"
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is MergeableToken -> this.tokens == other.tokens
        else -> false
    }
}
