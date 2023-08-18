package lushu.Merger.Lattice

interface Lattice<Node> {
    fun meet(n1: Node, n2: Node): Node
    fun topNode(): Node
    fun isTop(n: Node): Boolean
}
