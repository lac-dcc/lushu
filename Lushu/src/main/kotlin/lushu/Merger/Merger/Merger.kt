package lushu.Merger.Merger

import lushu.Merger.Config.Config
import lushu.Merger.Lattice.MergerLattice
import lushu.Merger.Lattice.NodeFactory

class Merger(
    private val nf: NodeFactory
) {
    private val lattice = MergerLattice(nf)

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
    fun merge(ns1: List<Token>, s: String): Result {
        val ns2 = tokensFromString(s)
        return merge(ns1, ns2)
    }

    // This overload is useful when you have no pre-built list of tokens ready.
    fun merge(s1: String, s2: String): Result {
        val ns1 = tokensFromString(s1)
        return merge(ns1, s2)
    }

    fun tokensFromString(s: String, sensitive: Boolean = false): List<Token> {
        return nf.buildIntervalNodes(s, sensitive)
    }

    companion object {
        fun fromConfigFile(fpath: String): Merger {
            val config = Config.fromConfigFile(fpath)
            return Merger(config.nodeFactory)
        }

        fun fromConfig(cfg: Config): Merger {
            return Merger(cfg.nodeFactory)
        }
    }

    private fun isSuccessful(merged: List<Token>): Boolean {
        return merged.size != 1 || !NodeFactory.isTop(merged[0])
    }
}
