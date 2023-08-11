package lushu.ContextGrammar.Grammar.Nodes

interface NonMergeableNode {

    val nonmergeable: Boolean

    /**
     * Determines whether the current object is mergeable.
     *
     * @return `true` if the object is mergeable, `false` otherwise.
     */
    fun isNonMergeable() : Boolean

    /**
     * Merges two mergeable nodes.
     *
     * This function takes two mergeable nodes as input and merges them into a single mergeable node.
     *
     * @param acc The accumulator mergeable node.
     * @param node The mergeable node to be merged with the accumulator.
     * @return A new mergeable node resulting from the merge of 'acc' and 'node'.
     */
    fun meetNonMergeable(acc: NonMergeableNode, n2: NonMergeableNode): NonMergeableNode
}
