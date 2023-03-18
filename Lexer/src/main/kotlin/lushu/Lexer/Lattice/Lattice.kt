package lushu.Lexer.Lattice

import lushu.Lexer.Lattice.Node.Node

// Lattice is any structure that has a well-defined meet operation.
interface Lattice {
    fun meet(n1: Node, n2: Node): Node
}
