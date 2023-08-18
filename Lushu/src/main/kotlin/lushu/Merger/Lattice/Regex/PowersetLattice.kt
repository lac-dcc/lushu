package lushu.Merger.Lattice

import lushu.Merger.Lattice.Node.PwsetNode
import org.slf4j.LoggerFactory

class PowersetLattice(
    private val nf: NodeFactory
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun meet(n1: PwsetNode, n2: PwsetNode): PwsetNode = when {
        n1.blacklists(n2) || n2.blacklists(n1) -> nf.topNode()
        NodeFactory.isTop(n1) || NodeFactory.isTop(n2) -> nf.topNode()
        else -> nf.joinPwsetNodes(n1, n2)
    }
}
