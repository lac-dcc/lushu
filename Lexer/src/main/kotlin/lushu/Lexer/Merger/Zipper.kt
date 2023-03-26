package lushu.Lexer.Merger

import lushu.Lexer.Lattice.LexerLattice
import lushu.Lexer.Lattice.Node.Node
import org.slf4j.LoggerFactory

class Zipper(
    private val lattice: LexerLattice
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun zip(
        ns1: List<Node>,
        ns2: List<Node>
    ): List<Node> {
        if (ns1.isEmpty()) {
            return ns2
        }
        if (ns1 == ns2) {
            return ns2
        }
        if (ns1.size != ns2.size) {
            // List of different sizes can only go to top, considering they have
            // already been formatted.
            return listOf<Node>(lattice.topNode())
        }

        var zippedNodes = listOf<Node>()
        for (i in 0 until ns1.size) {
            val glb = lattice.meet(ns1[i], ns2[i])
            if (lattice.isTop(glb)) {
                return listOf<Node>(glb)
            }
            zippedNodes += glb
        }

        return zippedNodes
    }
}
