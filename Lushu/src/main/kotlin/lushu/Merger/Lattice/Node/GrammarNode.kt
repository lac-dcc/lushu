package lushu.Merger.Lattice.Node

import lushu.Merger.Merger.Merger

class GrammarNode(
    var token: Token? = null,
    val star: Boolean = false,
    val mergeable: Boolean = false,
    val action: String = "",
    val endOfContext: Boolean = false,
    val parent: GrammarNode? = null,
    private val children: MutableList<GrammarNode> = mutableListOf(),

) {

    fun isStar(): Boolean {
        return this.star
    }

    fun isEndOfContext(): Boolean {
        return this.endOfContext
    }

    fun getAction(): String {
        return this.action
    }

    fun isMergeable(): Boolean {
        return this.mergeable
    }

    /**
     * Retrieves the list of child GrammarNode elements associated with this node.
     *
     * @return A List of GrammarNode objects representing the children of this node.
     */
    fun getChildren(): MutableList<GrammarNode> {
        return this.children
    }

    /**
     * Checks if two Node elements are equivalent.
     *
     * @param node1 The first Node element to compare.
     * @param node2 The second Node element to compare.
     * @return 'true' if the two nodes are equivalent, 'false' otherwise.
     */
    override fun equals(other: Any?): Boolean = when (other) {
        is GrammarNode -> (
            this.token.match(other.token) &&
                this.star == other.star &&
                this.mergeable == other.mergeable &&
                this.endOfContext == other.endOfContext
            )

        else -> false
    }

    /**
     * Finds the equivalent Node from the children list or returns the original node if not found.
     *
     * @param node The Node for which the equivalent node needs to be found.
     * @return The equivalent Node if found in the children list; otherwise, returns the original node.
     */
    fun getEquivalentNode(node: GrammarNode): GrammarNode {
        return getChildren().find { it == node } ?: node
    }

    /**
     * Filters the list of nodes based on their mergeable property.
     *
     * @param mergeable Determines whether to include mergeable or non-mergeable nodes in the result.
     *                    If 'true', only mergeable nodes will be included; otherwise, only non-mergeable nodes will be included.
     * @return A new list containing the filtered nodes based on the 'nonMergeable' parameter.
     */
    fun filterMergeblesNodes(mergeable: Boolean): List<GrammarNode> {
        return children.filter { it.isMergeable() == !mergeable }
    }

    /**
     * Merges the children nodes with the new node.
     *
     * @param newNode The new node that will be merged with the children nodes.
     */
    private fun mergeChildren(newNode: GrammarNode) {
        val mergeablesChildren = filterMergeblesNodes(true)
        val mergeablesNodes =
            MergerS.merger().recursiveMergeGrammarNodes(mergeablesChildren, listOf<GrammarNode>(newNode))
        val nonMergeablesNodes = filterMergeblesNodes(false)
        children.clear()
        children.addAll(nonMergeablesNodes + mergeablesNodes)
    }

    /**
     * Adds a new node to the children set.
     *
     * @param regex The node regular expression.
     * @param sensitive A boolean value indicating if the new node is sensitive.
     * @param starCase A boolean value indicating if the new node is a star case.
     * @param nonmergeable boolean value indicating if the new node is nonmergeable.
     */
    private fun addChild(
        tokens: List<Node>,
        sensitive: Boolean,
        star: Boolean,
        nonmergeable: Boolean,
        endOfContext: Boolean,
        children: MutableList<GrammarNode>,
    ) {
        val newNode = GrammarNode(tokens, sensitive, star, nonmergeable, endOfContext, this, children)
        addChild(newNode)
    }

    /**
     * Adds a new child node to the current node's children list.
     * @param newNode The node to be added as a child.
     */
    private fun addChild(newNode: GrammarNode) {
        if (newNode.isMergeable()) {
            children.add(firstIndex, newNode)
        } else {
            mergeChildren(newNode)
        }
    }

    private fun toToken(word: String, mergeable: Boolean): Token {
        if (mergeable) {
            return MergeableToken(word)
        }
        return NonMergeableToken(word)
    }

    /**
     * Finds an existing child node in the 'children' set that matches the provided criteria,
     * or adds a new node with the given properties if no match is found.
     *
     * @param regex The node regular expression.
     * @param sensitive A boolean value indicating if the new node is sensitive.
     * @param starCase A boolean value indicating if the new node is a star case.
     * @param nonmergeable A boolean value indicating if the new node is nonmergeable.
     * @return The existing child node that matches the criteria, if found. Otherwise, a new node
     *         with the given properties will be added to the 'children' set and returned.
     */
    fun findOrAddChild(
        word: String,
        star: Boolean,
        mergeable: Boolean,
        action: String,
        endOfContext: Boolean,
    ): GrammarNode {
        val token = toToken(word, mergeable)
        val newNode = GrammarNode(token, star, mergeable, action, endOfContext, this, mutableListOf())

        val existingChild = children.find { it == newNode }
        if (existingChild != null) {
            return existingChild
        }
        addChild(newNode)
        val resNode = getEquivalentNode(newNode)

        if (this.isStar()) {
            this.parent?.addChild(resNode)
        }

        return resNode
    }

    companion object {
        private val firstIndex = 0
    }
}
