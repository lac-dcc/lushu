package lushu.Lexer.Lattice

import lushu.Lexer.Lattice.Node.IntervalNode

class IntervalLattice(
    private val nf: NodeFactory
) {
    companion object {
        fun areCompatible(n1: IntervalNode, n2: IntervalNode): Boolean {
            return n1.baseNode == n2.baseNode
        }
    }

    fun meet(n1: IntervalNode, n2: IntervalNode): IntervalNode {
        // Assume interval nodes are compatible (have same base node)
        return IntervalNode(n1.baseNode, n1.joinCharset(n2), n1.joinInterval(n2))
    }
}
