package lushu.Merger.Merger

import lushu.Merger.Config.Config
import lushu.Merger.Lattice.MergerLattice
import lushu.Merger.Lattice.Node.GrammarNode
import lushu.Merger.Lattice.NodeFactory

class Merger(
    private val nf: NodeFactory,
) {
    private val lattice = MergerLattice(nf)

    private val reducer = Reducer(lattice)
    private val zipper = Zipper(lattice)

    data class Result(
        val success: Boolean,
        val tokens: List<Token>,
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

    private fun mergeGrammarNodesParents(n1: GrammarNode?, n2: GrammarNode?): GrammarNode?{
        if(n1 != null){
            return n1
        } 
        if(n2 != null){
            return n2
        }
        return null
    }

    fun mergeGrammarNodes(n1: GrammarNode, n2: GrammarNode): GrammarNode {
        if (n1.isNonMergeable() || n2.isNonMergeable()) {
            return null
        }
    
        val (newLatticeNodes, success) = merge(n1.latticeNodes, n2.latticeNodes)
        if (!success) {
            return null
        }
    
        val newSensitive = n1.isSensitive() || n2.isSensitive()
        val newStar = n1.isStar() || n2.isStar()
        val newNonMergeable = n1.isNonMergeable() || n2.isNonMergeable()
        val newTerminal = n1.isTerminal() || n2.isTerminal()
        val newParent = mergeGrammarNodesParents(n1, n2)
        val newChildren = recursiveMergeGrammarNodes(n1.getChildren(), n2.getChildren()).toMutableList()

        return GrammarNode(newLatticeNodes, newSensitive, newStar, newNonMergeable, newTerminal, newParent, newChildren)
    }

    fun recursiveMergeGrammarNodes(ln1: List<GrammarNode>, ln2: List<GrammarNode>): List<GrammarNode> {
        if(ln1.isNullOrEmpty()){
            return ln2
        }

        if(ln2.isNullOrEmpty()){
            return ln1
        }

        val (mergedNodes, nonMergedNodes) = ln1.mapNotNull { n1 ->
            ln2.find { n2 -> n1 == n2 }
                ?.let { n2 ->
                    val newNode = mergeGrammarNodes(n1, n2)
                    n1.update(newNode)
                    n2
                }
        }.partition { it in ln1 }

        return nonMergedNodes + ln2.filter { it !in mergedNodes }
    }

    fun tokensFromString(s: String, sensitive: Boolean = false): List<Token> {
        return reducer.reduce(nf.buildIntervalNodes(s, sensitive))
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
