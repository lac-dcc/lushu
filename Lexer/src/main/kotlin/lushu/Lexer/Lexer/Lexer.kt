package lushu.Lexer.Lexer

import lushu.Lexer.Config.Config
// import lushu.Lexer.Compressor.Compressor
// import lushu.Lexer.Lattice.LexerLattice
// import lushu.Lexer.Lattice.NodeFactory
import lushu.Lexer.Token.Token

class Lexer(config: Config) {
    // private val lattice = Lattice(config.baseNodes, config.disjointNodes)
    // private val compressor = Compressor(NodeFactory(), lattice)

    fun run(words: List<String>): List<Token> {
        // TODO: compressor.compressNodes()
        return listOf<Token>()
    }
}
