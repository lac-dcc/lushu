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

    fun merge(ns1: List<Token>, ns2: List<Token>): List<Token> {
        val reduc1 = reducer.reduce(ns1)
        val reduc2 = reducer.reduce(ns2)
        return zipper.zip(reduc1, reduc2)
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
}
