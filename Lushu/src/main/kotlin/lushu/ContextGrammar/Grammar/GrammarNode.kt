package lushu.ContextGrammar.Grammar

import lushu.Merger.Lattice.Node.Node
import lushu.Merger.Lattice.Node.Interval
import lushu.Merger.Lattice.Node.Charset

class GrammarNode(
    val star: Boolean = false,
    val nonmergeable: Boolean = false,
    val terminal: Boolean = false,
    val parent: GrammarNode? = null,
    private val children: MutableList<GrammarNode> = mutableListOf(),

    charset: Charset,
    interval: Interval,
    sensitive: Boolean = false

) : Node(charset, interval, sensitive) {

    fun isSensitive(): Boolean {
        return this.sensitive
    }

    fun isStar(): Boolean {
        return star
    }

    fun isNonMergeable(): Boolean {
        return nonmergeable
    }

    fun isTerminal(): Boolean{
        return terminal
    }
    /**
     * Retrieves the list of child GrammarNode elements associated with this node.
     *
     * @return A List of GrammarNode objects representing the children of this node.
     */
    fun getChildren(): List<GrammarNode> {
        return this.children
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
     * Determines if the given word matches the regular expression defined by the current instance.
     *
     * @param word to be matched against the regular expression.
     * @return true if the word matches the regular expression, false otherwise.
     */
    fun match(word: String): Boolean {
        /* TODO */
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
                this.charset == other.charset &&
                this.interval == other.interval &&
                this.sensitive == other.sensitive &&
                this.star == other.star &&
                this.nonmergeable == other.nonmergeable &&
                this.terminal == other.terminal
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
        return getChildren().find { it.equals(node) } ?: node
    }

    /**
     * Merges the children nodes of the new node.
     *
     * @param newNode The new node that will be merged with the children nodes.
     */
    private fun mergeChildren(newNode: GrammarNode) {
        val mergeablesNodes = filterMergeblesNodes(true)
        // val res = MergerS.merger().merge(ns1, ns2)
        // result <- merge all mergeablesNodes
        // forEach mergeablesNodes find the new regex and add it children
        val nonMergeablesNodes = filterMergeblesNodes(false)
        // include nonMergeable + result to this.children
    }

    /**
     * Adds a new node to the children set.
     *
     * @param regex The node regular expression.
     * @param sensitive A boolean value indicating if the new node is sensitive.
     * @param starCase A boolean value indicating if the new node is a star case.
     * @param nonmergeable boolean value indicating if the new node is nonmergeable.
     */
    private fun addChild(star: Boolean, nonmergeable: Boolean, terminal: Boolean, children: MutableList<GrammarNode>,
                         charset: Charset, interval: Interval, sensitive: Boolean ) {
        val newNode = GrammarNode(star, nonmergeable, terminal, this, children, charset, interval, sensitive)
        addChild(newNode)
    }

    /**
     * Adds a new child node to the current node's children list.
     * @param newNode The node to be added as a child.
     */
    private fun addChild(newNode: GrammarNode) {
        if(newNode.isNonMergeable()){
            children.add(firstIndex, newNode)
        }
        else{
            children.add(newNode)
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
    fun findOrAddChild(star: Boolean, nonmergeable: Boolean, terminal: Boolean, charset: Charset, interval: Interval, sensitive: Boolean): GrammarNode {
        val existingChild = children.find { it.match(regex) }
        if (existingChild != null) {
            return existingChild
        }

        val newNode = GrammarNode(star, nonmergeable, terminal, this, mutableListOf(), charset, interval, sensitive)
        addChild(newNode)
        if(this.isStar()){
            this.parent?.addChild(newNode)
        }

        if (!newNode.isNonMergeable()) {
            mergeChildren(newNode)
            return getEquivalentNode(newNode)
        }

        return newNode
    }
    companion object {
        private val firstIndex = 0
    }
}
