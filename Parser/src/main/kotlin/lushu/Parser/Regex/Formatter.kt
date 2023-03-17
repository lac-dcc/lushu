package lushu.ParSy.Regex

import org.slf4j.LoggerFactory

class Formatter(private val lattice: Lattice) {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

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
            if (glb.isTop) {
                formattedNodes += origNode
                fmtIdx++
                continue
            }

            // We want a node [a]{1,1}[b]{1,1}[c]{1,1} to become [abc]{3,3}
            formattedNodes[fmtIdx] = glb.apply {
                if (!isKleene() && !lattice.isBaseNode(glb)) {
                    capInterval()
                    incrementInterval()
                }
            }
        }

        logger.debug("Formatted nodes: $formattedNodes")

        return formattedNodes
    }
}
