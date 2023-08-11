package lushu.ContextGrammar.Grammar.Nodes

interface PlusNode {

    val plus: Boolean

    /**
     * Checks if the current instance represents a "plus".
     *
     * @return True if the instance represents a "plus", false otherwise.
     */
    fun isPlus() : Boolean

    /**
     * Combines two PlusNodes and returns a PlusNode.
     *
     * This function takes two PlusNodes and determines the result of their combination.
     * It prioritizes the PlusNode that has the `plus` property set to true. If both PlusNodes
     * have the `plus` property set to true, the `acc` PlusNode is returned.
     *
     * @param acc The first PlusNode to be combined.
     * @param node The second PlusNode to be combined.
     * @return A PlusNode representing the combined result.
     */
    fun meetPlus(acc: PlusNode, n2: PlusNode): PlusNode

}