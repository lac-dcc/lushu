package lushu.Merger.Lattice.Node

interface Token {
    var tokens: List<Node>
    fun setToken(pattern: String)

    fun match(string: String): Boolean
}
