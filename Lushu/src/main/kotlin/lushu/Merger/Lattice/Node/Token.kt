package lushu.Merger.Lattice.Node

interface Token {
    var tokens: List<Node>
    fun setToken(pattern: String)

    fun match(string: String): Boolean
    fun match(tokens: List<Node>): Boolean
    fun equals(other: Token?): Boolean = when (other) {
        is Token -> this.tokens == other.tokens
        else -> false
    }
}
