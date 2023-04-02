package lushu.Merger.Lattice

import lushu.Merger.Lattice.Node.IntervalNode
import org.slf4j.LoggerFactory

class IntervalLattice(
    private val nf: NodeFactory
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        fun areCompatible(n1: IntervalNode, n2: IntervalNode): Boolean {
            return n1.baseNode == n2.baseNode
        }
    }

    fun meet(n1: IntervalNode, n2: IntervalNode): IntervalNode {
        logger.debug("Meeting nodes $n1 and $n2 in interval lattice")
        // Assume interval nodes are compatible (have same base node)
        return IntervalNode(
            n1.baseNode,
            n1.joinCharset(n2),
            n1.joinInterval(n2),
            n1.joinSensitive(n2)
        )
    }
}
