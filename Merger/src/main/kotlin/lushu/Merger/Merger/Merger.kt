package lushu.Merger.Merger

import lushu.Merger.Lattice.MergerLattice

class Merger(
    private val lattice: MergerLattice
) {
    private val reducer = Reducer(lattice)
    private val zipper = Zipper(lattice)

    fun merge(ns1: List<Token>, ns2: List<Token>): List<Token> {
        val reduc1 = reducer.reduce(ns1)
        val reduc2 = reducer.reduce(ns2)
        return zipper.zip(reduc1, reduc2)
    }
}
