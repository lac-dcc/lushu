package lushu.Merger.Lattice

import lushu.Merger.Lattice.Node.Node
import lushu.Merger.Lattice.Node.PwsetNode

class NodePrinter {
    companion object {
        val topRepr = ".+"

        fun print(n: Node): String {
            if (NodeFactory.isTop(n)) {
                return topRepr
            }
            val cs = n.charset
            val itvl = n.interval
            if (n is PwsetNode) {
                return "[${cs.collapse()}]+"
            } else {
                return "[${cs.collapse()}]{${itvl.first},${itvl.second}}"
            }
        }

        fun print(nodes: List<Node>): String {
            var s = ""
            nodes.forEach {
                s += print(it)
            }
            return s
        }
    }
}
