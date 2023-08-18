package lushu.Merger.Merger

import lushu.Merger.Config.Config
import lushu.Merger.Lattice.Regex.Lattice as RegexLattice
import lushu.Merger.Lattice.Lattice
import lushu.Merger.Lattice.NodeFactory

class Merger(
    private val nf: NodeFactory
    private val lattice: Lattice = RegexLattice(nf)
) {

    private val reducer = Reducer(lattice)
    private val zipper = Zipper(lattice)

    data class Result(
        val success: Boolean,
        val tokens: List<Token>
    )

    fun merge(ns1: List<Token>, ns2: List<Token>): Result {
        val reduc1 = reducer.reduce(ns1)
        val reduc2 = reducer.reduce(ns2)
        val merged = zipper.zip(reduc1, reduc2)
        return Result(isSuccessful(merged), merged)
    }

    // This overload is useful when you have a pre-built list of tokens ready,
    // and you need to merge in a string in an online manner.
    fun merge(ns1: List<Token>, s: String): Result = merge(ns1, tokensFromString(s))

    // This overload is useful when you have no pre-built list of tokens ready.
    fun merge(s1: String, s2: String): Result = merge(tokensFromString(s1), s2)

    fun tokensFromString(s: String, sensitive: Boolean = false): List<Token> =
        reducer.reduce(nf.buildIntervalNodes(s, sensitive))

    companion object {
        fun fromConfigFile(fpath: String): Merger =
            Config.fromConfigFile(fpath).let { Merger(it.nodeFactory) }

        fun fromConfig(cfg: Config): Merger = Merger(cfg.nodeFactory)
    }

    private fun isSuccessful(merged: List<Token>): Boolean =
        merged.size != 1 || !NodeFactory.isTop(merged[0])
}
