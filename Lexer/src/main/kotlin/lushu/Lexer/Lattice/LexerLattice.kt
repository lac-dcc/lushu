package lushu.Lexer.Lattice

import lushu.Lexer.Lattice.Node.IntervalNode
import lushu.Lexer.Lattice.Node.Node
import lushu.Lexer.Lattice.Node.PwsetNode
import org.slf4j.LoggerFactory

class LexerLattice(
    private val nf: NodeFactory
) : Lattice {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val platt = PowersetLattice(nf)
    private val ilatt = IntervalLattice(nf)

    override fun meet(n1: Node, n2: Node): Node {
        logger.debug("Meeting nodes $n1 and $n2")

        if (isTop(n1) || isTop(n2)) {
            return topNode()
        }

        if (n1 is PwsetNode || n2 is PwsetNode ||
            !IntervalLattice.areCompatible(n1 as IntervalNode, n2 as IntervalNode)
        ) {
            return elevateAndMeetInPowerset(n1, n2)
        }

        val intervalNode = ilatt.meet(n1, n2)
        if (!intervalNode.isWithinBounds()) {
            return elevateAndMeetInPowerset(n1, n2)
        }
        return intervalNode
    }

    fun isTop(n: Node): Boolean = NodeFactory.isTop(n)
    fun topNode(): Node = nf.topNode()

    private fun elevateToPowerset(n: Node): PwsetNode = when (n) {
        is PwsetNode -> n
        is IntervalNode -> n.baseNode
    }

    private fun elevateAndMeetInPowerset(n1: Node, n2: Node): Node {
        return platt.meet(elevateToPowerset(n1), elevateToPowerset(n2))
    }
}
