package lushu.Merger.Lattice.Node

import lushu.Merger.Merger.Token
import lushu.Merger.Merger.Merger
import lushu.Merger.Lattice.NodeFactory

class GrammarNode(
    var tokens: List<Node> = listOf(),
    val sensitive: Boolean = false,
    val star: Boolean = false,
    val nonmergeable: Boolean = false,
    val terminal: Boolean = false,
    val parent: GrammarNode? = null,
    private val children: MutableList<GrammarNode> = mutableListOf()

) {

    fun isSensitive(): Boolean {
        return this.sensitive
    }

    fun isStar(): Boolean {
        return this.star
    }

    fun isNonMergeable(): Boolean {
        return this.nonmergeable
    }

    fun isTerminal(): Boolean {
        return this.terminal
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
     * Determines if the given word matches the regular expression defined by the current instance.
     *
     * @param word to be matched against the regular expression.
     * @return true if the word matches the regular expression, false otherwise.
     */
    fun match(word: String): Boolean {
        val res = MergerS.merger().merge(this.tokens, word)
        if(res.success && !this.isNonMergeable()){
            this.tokens
        }
        return res.success
    }

    fun match(tokens: List<Node>): Boolean {
        val res = MergerS.merger().merge(this.tokens, tokens)
        if(res.success && !this.isNonMergeable()){
            this.tokens = res.tokens
        }
        return res.success
    }
    /**
     * Checks if two Node elements are equivalent.
     *
     * @param node1 The first Node element to compare.
     * @param node2 The second Node element to compare.
     * @return 'true' if the two nodes are equivalent, 'false' otherwise.
     */
    fun isEquals(other: Any?): Boolean = when (other) {
        is GrammarNode -> (
                    this.match(other.tokens) &&
                        this.sensitive == other.sensitive &&
                        this.star == other.star &&
                        this.nonmergeable == other.nonmergeable &&
                        this.terminal == other.terminal
                )

        else -> false
    }

    /* TODO */
    /* While the updater only modifies tokens and children */
    fun update(element: GrammarNode) {
        if (element.tokens.isNotEmpty()) {
            this.tokens = element.tokens
        }
        // add sensitive case
        if (element.children.isNotEmpty()) {
            this.children.clear()
            this.children.addAll(element.children)
        }
    }


    /**
     * Finds the equivalent Node from the children list or returns the original node if not found.
     *
     * @param node The Node for which the equivalent node needs to be found.
     * @return The equivalent Node if found in the children list; otherwise, returns the original node.
     */
    fun getEquivalentNode(node: GrammarNode): GrammarNode {
        return getChildren().find { it.isEquals(node) } ?: node
    }

    /**
     * Filters the list of nodes based on their mergeable property.
     *
     * @param mergeable Determines whether to include mergeable or non-mergeable nodes in the result.
     *                    If 'true', only mergeable nodes will be included; otherwise, only non-mergeable nodes will be included.
     * @return A new list containing the filtered nodes based on the 'nonMergeable' parameter.
     */
    fun filterMergeblesNodes(mergeable: Boolean): List<GrammarNode> {
        return children.filter { it.isNonMergeable() == !mergeable }
    }

    /**
     * Merges the children nodes of the new node.
     *
     * @param newNode The new node that will be merged with the children nodes.
     */
    private fun mergeChildren(newNode: GrammarNode) {
        val mergeablesNodes = filterMergeblesNodes(true)
        val mergeableNodes = MergerS.merger().recursiveMergeGrammarNodes(children, listOf<GrammarNode>(newNode))
        val nonMergeablesNodes = filterMergeblesNodes(false)
        children.clear()
        children.addAll(nonMergeablesNodes + mergeableNodes)
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
        terminal: Boolean,
        children: MutableList<GrammarNode>
    ) {
        val newNode = GrammarNode(tokens, sensitive, star, nonmergeable, terminal, this, children)
        addChild(newNode)
    }

    /**
     * Adds a new child node to the current node's children list.
     * @param newNode The node to be added as a child.
     */
    private fun addChild(newNode: GrammarNode) {
        if (newNode.isNonMergeable()) {
            children.add(firstIndex, newNode)
        } else {
            mergeChildren(newNode)
        }
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
        sensitive: Boolean,
        star: Boolean,
        nonmergeable: Boolean,
        terminal: Boolean
    ): GrammarNode {
        val token = MergerS.merger().tokensFromString(word)
        val newNode = GrammarNode(token, sensitive, star, nonmergeable, terminal, this, mutableListOf())

        val existingChild = children.find { it.equals(newNode) }
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

    fun treeToStringPreorder(current: GrammarNode, level: Int = 0): String {
        val indentation = "-".repeat(level)
        val result = StringBuilder()

        result.append("$indentation${current.tokens}\n")
        
        if(!current.children.isNullOrEmpty()){
            current.children.forEach{ child ->
                result.append(treeToStringPreorder(child, level + 1))
            }
        }
        return result.toString()
    }

    override fun toString(): String {
        return treeToStringPreorder(this)
    }

    companion object {
        private val firstIndex = 0
    }
}
