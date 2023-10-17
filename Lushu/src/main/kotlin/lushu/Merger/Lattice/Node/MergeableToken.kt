package lushu.Merger.Lattice.Node

class MergeableToken(
    pattern: String,
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

    fun match(tokens: List<Node>): Boolean {
        val res = MergerS.merger().merge(this.tokens, tokens)
        if (res.success) {
            this.tokens = res.tokens
        }
        return res.success
    }
    override fun toString(): String {
        return "${this.tokens}"
    }
}
