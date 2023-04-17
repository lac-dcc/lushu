package lushu.Merger.Lattice

import lushu.Merger.Lattice.Node.PwsetNode
import org.slf4j.LoggerFactory

class PowersetLattice(
    private val nf: NodeFactory
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun meet(n1: PwsetNode, n2: PwsetNode): PwsetNode {
        logger.debug("Meeting nodes $n1 and $n2 in powerset lattice")
        if (n1.blacklists(n2) || n2.blacklists(n1)) {
            logger.debug("Nodes are disjoint")
            return nf.topNode()
        }
        return nf.joinPwsetNodes(n1, n2)
    }
}
