package lushu.Lexer.Lattice

import lushu.Lexer.Lattice.Node.Node
import lushu.Lexer.Lattice.Node.PwsetNode

class NodePrinter {
    companion object {
        fun print(nodes: List<Node>): String {
            // Special case for top node
            if (nodes.size == 1 && NodeFactory.isTop(nodes[0])) {
                return ".*"
            }

            var s = ""
            nodes.forEach {
                val cs = it.charset
                val itvl = it.interval
                if (it is PwsetNode) {
                    s += "[${cs.collapse()}]*"
                } else {
                    s += "[${cs.collapse()}]{${itvl.first},${itvl.second}}"
                }
            }
            return s
        }
    }
}
