package lushu.Lexer.Lexer

import lushu.Lexer.Config.Config
import lushu.Lexer.Token.Token
import lushu.Lexer.Regex.Lattice
import lushu.Lexer.Regex.Compressor
import lushu.Lexer.Regex.NodeFactory

class Lexer(config: Config) {
    private val lattice = Lattice(config.baseNodes, config.disjointNodes)
    private val compressor = Compressor(NodeFactory(), lattice)

    fun run(words: List<String>): List<Token> {
        // TODO: compressor.compressNodes()
        return listOf<Token>()
    }
}
