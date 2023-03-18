package lushu.Lexer.Compressor

import lushu.Lexer.Lattice.LexerLattice
import lushu.Lexer.Lattice.Node.IntervalNode
import lushu.Lexer.Lattice.Node.Node
import org.slf4j.LoggerFactory

class Formatter(private val lattice: LexerLattice) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun formatNodes(nodes: List<Node>): List<Node> {
        logger.debug("Formatting nodes: $nodes")

        if (nodes.isEmpty()) {
            return listOf<Node>()
        } else if (nodes.size == 1) {
            return nodes
        }

        val formattedNodes = mutableListOf<Node>(nodes[0])
        var fmtIdx = 0
        var origIdx = 1
        while (origIdx < nodes.size) {
            // Expand upper bound to accomodate one more character.
            val origNode = nodes[origIdx]
            val fmtNode = formattedNodes[fmtIdx]
            origIdx++
            val glb = lattice.meet(fmtNode, origNode)
            if (lattice.isTop(glb)) {
                formattedNodes += origNode
                fmtIdx++
                continue
            }

            // We want a node [a]{1,1}[b]{1,1}[c]{1,1} to become [abc]{3,3}
            formattedNodes[fmtIdx] = if (glb is IntervalNode) {
                (glb as IntervalNode).apply {
                    capInterval()
                    incrementInterval()
                }
            } else {
                glb
            }
        }

        logger.debug("Formatted nodes: $formattedNodes")

        return formattedNodes
    }
}
