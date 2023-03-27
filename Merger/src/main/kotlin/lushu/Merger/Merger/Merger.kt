package lushu.Merger.Merger

import lushu.Merger.Lattice.MergerLattice
import lushu.Merger.Lattice.Node.Node

class Merger(
    private val lattice: MergerLattice
) {
    private val reducer = Reducer(lattice)
    private val zipper = Zipper(lattice)

    fun merge(ns1: List<Node>, ns2: List<Node>): List<Node> {
        val reduc1 = reducer.reduce(ns1)
        val reduc2 = reducer.reduce(ns2)
        return zipper.zip(reduc1, reduc2)
    }
}
