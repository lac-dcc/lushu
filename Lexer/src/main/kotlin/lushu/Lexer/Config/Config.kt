package lushu.Lexer.Config

import java.io.File

import lushu.Lexer.Regex.Node

class Config(
    val baseNodes: List<Node>,
    val disjointNodes: List<Pair<Node,Node>>,
) {
    companion object {
        fun fromCLIArgs(args: List<String>): Config {
            val configFPath = args[0]
            return fromConfigFile(configFPath)
        }

        fun fromConfigFile(configFPath: String): Config {
            val baseNodes = listOf<Node>()
            val disjointNodes = listOf<Pair<Node, Node>>()
            return Config(baseNodes, disjointNodes)
        }
    }

    fun isEmpty(): Boolean {
        // If there are no base nodes, then we have nothing to do, so we
        // consider the given configuration empty.
        return baseNodes.isEmpty()
    }
}
