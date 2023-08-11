package lushu.ContextGrammar.Grammar.Nodes

interface SensitiveNode{

    val sensitive: Boolean

    /**
     * Checks whether the data is sensitive.
     *
     * @return `true` if the data is sensitive, `false` otherwise.
     */
    fun isSensitive() : Boolean

    /**
     * Combines two SensitiveNodes, prioritizing sensitivity.
     *
     * @param acc The accumulator SensitiveNode.
     * @param node The SensitiveNode to be merged.
     * @return The merged SensitiveNode, with sensitivity prioritized.
     */
    fun meetSensitive(acc: SensitiveNode, n2: SensitiveNode) : SensitiveNode
}