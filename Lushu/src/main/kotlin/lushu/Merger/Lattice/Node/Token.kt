package lushu.Merger.Lattice.Node

interface Token {
    var tokens: List<Node>
    fun setToken(pattern: String)

    fun match(string: String): Boolean
    fun match(tokens: List<Node>): Boolean
    override fun equals(other: Any?): Boolean
}
