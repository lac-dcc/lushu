package lushu.ContextGrammar.Grammar

import lushu.ContextGrammar.Grammar.Nodes.NonMergeableNode
import lushu.ContextGrammar.Grammar.Nodes.PlusNode
import lushu.ContextGrammar.Grammar.Nodes.RegexNode
import lushu.ContextGrammar.Grammar.Nodes.SensitiveNode

class GrammarNode(
    override var regex: String = "",
    override var sensitive: Boolean = false,
    override var plus: Boolean = false,
    override var nonmergeable: Boolean = false,

    private val children: MutableList<GrammarNode> = mutableListOf()

) : RegexNode, SensitiveNode, PlusNode, NonMergeableNode {

    fun meetGrammarNode(acc: GrammarNode, node: GrammarNode) : GrammarNode{
        var res: GrammarNode = acc
        meetRegex(res, node)
        meetSensitive(res, node)
        meetPlus(res, node)
        meetNonMergeable(res, node)
        return res
    }

    override fun meetRegex(acc: RegexNode, node: RegexNode): RegexNode {
        if(acc !is GrammarNode || node !is GrammarNode){
            throw IllegalArgumentException("Incompatible element types")
        }
        if (Regex(acc.getRegex()).matches(node.getRegex())) {
            return acc
        } else {
            // return merger(acc, node)
            return acc
        }
    }

    override fun getRegex() : String{
        return regex
    }

    override fun meetSensitive(acc: SensitiveNode, node: SensitiveNode): SensitiveNode {
        if(acc !is GrammarNode || node !is GrammarNode){
            throw IllegalArgumentException("Incompatible element types")
        }
        if (acc.isSensitive()) {
            return acc
        }
        if (node.isSensitive()) {
            acc.sensitive = node.isSensitive()
        }
        return acc
    }

    override fun isSensitive(): Boolean {
        return this.sensitive
    }

    override fun meetPlus(acc: PlusNode, node: PlusNode): PlusNode {
        if(acc !is GrammarNode || node !is GrammarNode){
            throw IllegalArgumentException("Incompatible element types")
        }
        if (acc.isPlus()) {
            return acc
        }
        if (node.isPlus()) {
            acc.plus = true
        }
        return acc
    }

    override fun isPlus(): Boolean {
        return plus
    }

    override fun meetNonMergeable(acc: NonMergeableNode, node: NonMergeableNode): NonMergeableNode {
        if(acc !is GrammarNode || node !is GrammarNode){
            throw IllegalArgumentException("Incompatible element types")
        }
        return acc
    }

    override fun isNonMergeable(): Boolean {
        return nonmergeable
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
        return Regex(this.getRegex()).matches(word)
    }

    /**
     * Checks if two Node elements are equivalent.
     *
     * @param node1 The first Node element to compare.
     * @param node2 The second Node element to compare.
     * @return 'true' if the two nodes are equivalent, 'false' otherwise.
     */
    fun areNodesEquivalent(node1: GrammarNode, node2: GrammarNode): Boolean {
        return (node1.match(node2.regex) || node2.match(node1.regex)) &&
                node1.isSensitive() == node2.isSensitive() &&
                node1.isPlus() == node2.isPlus() &&
                node1.isNonMergeable() == node2.isNonMergeable()
    }

    /**
     * Finds the equivalent Node from the children list or returns the original node if not found.
     *
     * @param node The Node for which the equivalent node needs to be found.
     * @return The equivalent Node if found in the children list; otherwise, returns the original node.
     */
    fun getEquivalentNode(node: GrammarNode): GrammarNode {
        return getChildren().find { areNodesEquivalent(it, node) } ?: node
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
     * @param plusCase A boolean value indicating if the new node is a plus case.
     * @param nonmergeable boolean value indicating if the new node is nonmergeable.
     */
    private fun addChild(r: String, s: Boolean, pc: Boolean, m: Boolean) {
        val newNode = GrammarNode(r, s, pc, m)
        this.children.add(newNode)
    }

    /**
     * Adds a new child node to the current node's children list.
     * @param newNode The node to be added as a child.
     */
    private fun addChild(newNode: GrammarNode) {
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
    fun findOrAddChild(regex: String, sensitive: Boolean, plusCase: Boolean, nonmergeable: Boolean): GrammarNode {
        val existingChild = children.find { it.match(regex) }
        if (existingChild != null) {
            return existingChild
        }

        val newNode = GrammarNode(regex, sensitive, plusCase, nonmergeable)
        addChild(newNode)

        if (!newNode.isNonMergeable()) {
            mergeChildren(newNode)
            return getEquivalentNode(newNode)
        }

        return newNode
    }
}
