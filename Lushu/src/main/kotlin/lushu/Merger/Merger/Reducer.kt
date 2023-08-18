package lushu.Merger.Merger

import lushu.Merger.Lattice.MergerLattice
import lushu.Merger.Lattice.Node.IntervalNode
import lushu.Merger.Lattice.Node.Node
import org.slf4j.LoggerFactory

class Reducer(private val lattice: MergerLattice) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun reduce(nodes: List<Node>): List<Node> {
        if (nodes.isEmpty()) {
            return listOf<Node>()
        }
        if (nodes.size == 1) {
            return nodes
        }

        val reducedNodes = mutableListOf<Node>(nodes[0])
        var fmtIdx = 0
        var origIdx = 1
        while (origIdx < nodes.size) {
            // Expand upper bound to accomodate one more character.
            val origNode = nodes[origIdx]
            val fmtNode = reducedNodes[fmtIdx]
            origIdx++
            val glb = lattice.meet(fmtNode, origNode)
            if (lattice.isTop(glb)) {
                reducedNodes += origNode
                fmtIdx++
                continue
            }

            // We want a node [a]{1,1}[b]{1,1}[c]{1,1} to become [abc]{3,3}
            reducedNodes[fmtIdx] = if (glb is IntervalNode) {
                with(glb) {
                    IntervalNode(
                        baseNode,
                        charset,
                        interval.cap().increment(),
                        sensitive
                    )
                }
            } else {
                glb
            }
        }

        return reducedNodes
    }
}
