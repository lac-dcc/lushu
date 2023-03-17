package lushu.Lexer.Regex

import org.slf4j.LoggerFactory

data class CompressionResult(
    val rule: String,
    val isTop: Boolean
)

class Compressor(
    private val nf: NodeFactory,
    private val lattice: Lattice
) {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    private val formatter = Formatter(lattice)

    companion object {
        fun newBasic(): Compressor {
            return Compressor(
                NodeFactory(),
                Lattice(listOf<Node>(), listOf<Pair<Node, Node>>())
            )
        }
    }

    fun compressNodes(
        // We assume the previous regexes already are well-behaved. So we only
        // need to format the incoming, new, token nodes.
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
            // List of different sizes can only go to top (since they have
            // already been formatted).
            return listOf(topNode())
        }

        var compressedNodes = listOf<Node>()
        for (i in 0 until fmtPrev.size) {
            val glb = lattice.meet(fmtPrev[i], fmtTokenNodes[i])
            if (glb.isTop) {
                return listOf(topNode())
            }
            compressedNodes += glb
        }

        return compressedNodes
    }

    fun compress(prevRegex: String, token: String): CompressionResult {
        val prevRegexNodes = nf.parseNodes(prevRegex)
        val tokenNodes = nf.parseNodes(token)
        val compressedNodes = compressNodes(prevRegexNodes, tokenNodes)
        if (compressedNodes.size == 1 && compressedNodes[0].isTop) {
            return CompressionResult(dotStar, true)
        }
        return CompressionResult(NodeFactory().buildString(compressedNodes), false)
    }
}
