package lushu.Lexer.Config

// YAML represents a YAML config file we expect to receive as an input.
data class ConfigYAML(
    val latticeBase: Map<String, BaseNode>
)

data class BaseNode(
    val intervalMin: Int = 1,
    val intervalMax: Int,
    val charset: String,
    val blacklist: Set<String>?
)
