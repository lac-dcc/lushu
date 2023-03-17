package lushu.Lexer.Config

// YAML represents a YAML config file we expect to receive as an input.
data class ConfigYAML(
    val base: Map<String, BaseNode>,
    val disjoint: Map<String, DisjointNode>?
)

data class BaseNode(
    val intervalMin: Int,
    val intervalMax: Int,
    val charset: String
)

data class DisjointNode(
    val todo: String
)
