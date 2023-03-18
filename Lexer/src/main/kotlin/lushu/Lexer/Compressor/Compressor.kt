package lushu.Lexer.Compressor

import lushu.Lexer.Lattice.LexerLattice
import lushu.Lexer.Lattice.Node.Node
import org.slf4j.LoggerFactory

class Compressor(
    private val lattice: LexerLattice
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val formatter = Formatter(lattice)

    companion object {
        val dotStar = ".*"

        fun topCompressionResult(): CompressionResult {
            return CompressionResult(dotStar, true)
        }

        data class CompressionResult(
            val rule: String,
            val isTop: Boolean
        )
    }

    fun compressNodes(
        prevRegexNodes: List<Node>,
        tokenNodes: List<Node>
    ): List<Node> {
        val fmtPrev = formatter.formatNodes(prevRegexNodes)
        val fmtTokenNodes = formatter.formatNodes(tokenNodes)

        if (fmtPrev.isEmpty()) {
            return fmtTokenNodes
        }
        if (fmtPrev == fmtTokenNodes) {
            return fmtTokenNodes
        }
        if (fmtPrev.size != fmtTokenNodes.size) {
            // List of different sizes can only go to top, considering they have
            // already been formatted.
            return listOf<Node>(lattice.topNode())
        }

        var compressedNodes = listOf<Node>()
        for (i in 0 until fmtPrev.size) {
            val glb = lattice.meet(fmtPrev[i], fmtTokenNodes[i])
            if (lattice.isTop(glb)) {
                return listOf<Node>(glb)
            }
            compressedNodes += glb
        }

        return compressedNodes
    }

    // fun compress(prevRegex: String, token: String): CompressionResult {
    //     val prevRegexNodes = NodeParser.parseNodes(prevRegex)
    //     val tokenNodes = NodeParser.parseNodes(token)
    //     val compressedNodes = compressNodes(prevRegexNodes, tokenNodes)
    //     if (compressedNodes.size == 1 && lattice.isTop(compressedNodes[0])) {
    //         return topCompressionResult()
    //     }
    //     return CompressionResult(NodeParser.buildString(compressedNodes), false)
    // }
}
