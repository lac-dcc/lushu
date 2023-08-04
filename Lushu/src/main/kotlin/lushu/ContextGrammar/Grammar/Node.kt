package lushu.ContextGrammar.Grammar

class Node(
    // private val token: Token = Token()
    private val regex: String = "",
    private val sensitive: Boolean = false,
    private val star: Boolean = false,
    private val nonmergeable: Boolean = false,
    private val children: MutableList<Node> = mutableListOf(),
) {

    fun getRegex(): String {
        return this.regex
    }

    fun getChildren(): List<Node> {
        return this.children
    }

    /**
     * Checks if the node is sensitive.
     *
     * @return true if the current node is sensitive, false otherwise.
     */
    fun isSensitive(): Boolean {
        return this.sensitive
    }

    /**
     * Checks if the node is star type.
     *
     * @return true if the current node is a star type, false otherwise.
     */
    fun isStar(): Boolean {
        return this.star
    }

    /**
     * Checks if the node is nonmergeable.
     *
     * @return true if the current node is a nonmergeable, false otherwise.
     */
    fun isNonMergeable(): Boolean {
        return this.nonmergeable
    }

    /**
     * Determines if the given word matches the regular expression defined by the current instance.
     *
     * @param word to be matched against the regular expression.
     * @return true if the word matches the regular expression, false otherwise.
     */
    fun match(word: String): Boolean {
        return Regex(this.getRegex()).matches(word)
    }

    /**
     * Filters the list of nodes based on their mergeable property.
     *
     * @param nonMergeable Determines whether to include mergeable or non-mergeable nodes in the result.
     *                    If 'true', only non-mergeable nodes will be included; otherwise, only mergeable nodes will be included.
     * @return A new list containing the filtered nodes based on the 'nonMergeable' parameter.
     */
    fun filterMergeblesNodes(nonMergeable: Boolean): List<Node> {
        return children.filter { it.isNonMergeable() == nonMergeable }
    }

    /**
     * Checks if two Node elements are equivalent.
     *
     * @param node1 The first Node element to compare.
     * @param node2 The second Node element to compare.
     * @return 'true' if the two nodes are equivalent, 'false' otherwise.
     */
    fun areNodesEquivalent(node1: Node, node2: Node): Boolean {
        return (node1.match(node2.regex) || node2.match(node1.regex)) &&
            node1.isSensitive() == node2.isSensitive() &&
            node1.isStar() == node2.isStar() &&
            node1.isNonMergeable() == node2.isNonMergeable()
    }

    /**
     * Finds the equivalent Node from the children list or returns the original node if not found.
     *
     * @param node The Node for which the equivalent node needs to be found.
     * @return The equivalent Node if found in the children list; otherwise, returns the original node.
     */
    fun getEquivalentNode(node: Node): Node {
        return getChildren().find { areNodesEquivalent(it, node) } ?: node
    }

    /**
     * Merges the children nodes of the new node.
     *
     * @param newNode The new node that will be merged with the children nodes.
     */
    private fun mergeChildren(newNode: Node) {
        val mergeablesNodes = filterMergeblesNodes(false)
        // val res = MergerS.merger().merge(ns1, ns2)
        // result <- merge all mergeablesNodes
        // forEach mergeablesNodes find the new regex and add it children
        val nonMergeablesNodes = filterMergeblesNodes(true)
        // include nonMergeable + result to this.children
    }

    /**
     * Adds a new node to the children set.
     *
     * @param regex The node regular expression.
     * @param sensitive A boolean value indicating if the new node is sensitive.
     * @param plusCase A boolean value indicating if the new node is a plus case.
     * @param nonmergeable boolean value indicating if the new node is nonmergeable.
     */
    private fun addChild(r: String, s: Boolean, pc: Boolean, m: Boolean) {
        val newNode = Node(r, s, pc, m)
        this.children.add(newNode)
    }

    /**
     * Adds a new child node to the current node's children list.
     * @param newNode The node to be added as a child.
     */
    private fun addChild(newNode: Node) {
        children.add(newNode)
    }

    /**
     * Finds an existing child node in the 'children' set that matches the provided criteria,
     * or adds a new node with the given properties if no match is found.
     *
     * @param regex The node regular expression.
     * @param sensitive A boolean value indicating if the new node is sensitive.
     * @param plusCase A boolean value indicating if the new node is a plus case.
     * @param nonmergeable A boolean value indicating if the new node is nonmergeable.
     * @return The existing child node that matches the criteria, if found. Otherwise, a new node
     *         with the given properties will be added to the 'children' set and returned.
     */
    fun findOrAddChild(regex: String, sensitive: Boolean, plusCase: Boolean, nonmergeable: Boolean): Node {
        val existingChild = children.find { it.match(regex) }
        if (existingChild != null) {
            return existingChild
        }

        val newNode = Node(regex, sensitive, plusCase, nonmergeable)
        addChild(newNode)

        if (!newNode.isNonMergeable()) {
            mergeChildren(newNode)
            return getEquivalentNode(newNode)
        }

        return newNode
    }
}
