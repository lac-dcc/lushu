package lushu.ContextGrammar.Grammar

class Node(
    private val regex: String = "",
    private val sensitive: Boolean = false,
    private val star: Boolean = false,
    private val terminal: Boolean = false,
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
     * @return true if the current node is sensitive, false otherwise.
     */
    fun isSensitive(): Boolean {
        return this.sensitive
    }

    /**
     * Checks if the node is star type.
     * @return true if the current node is a star type, false otherwise.
     */
    fun isPlus(): Boolean {
        return this.star
    }

    /**
     * Checks if the node is terminal.
     * @return true if the current node is a terminal, false otherwise.
     */
    fun isTerminal(): Boolean {
        return this.terminal
    }

    /**
     * Determines if the given word matches the regular expression defined by the current instance.
     * @param word to be matched against the regular expression.
     * @return true if the word matches the regular expression, false otherwise.
     */
    fun match(word: String): Boolean {
        return Regex(this.getRegex()).matches(word)
    }

    /**
     * Adds a new node to the children set.
     * @param regex The node regular expression.
     * @param sensitive A boolean value indicating if the new node is sensitive.
     * @param plusCase A boolean value indicating if the new node is a plus case.
     * @param terminal A boolean value indicating if the new node is terminal.
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
        if (newNode != null) {
            this.children.add(newNode)
        }
    }

    /**
     * Finds an existing child node in the 'children' set that matches the provided criteria,
     * or adds a new node with the given properties if no match is found.
     *
     * @param regex The node regular expression.
     * @param sensitive A boolean value indicating if the new node is sensitive.
     * @param plusCase A boolean value indicating if the new node is a plus case.
     * @param terminal A boolean value indicating if the new node is terminal.
     * @return The existing child node that matches the criteria, if found. Otherwise, a new node
     *         with the given properties will be added to the 'children' set and returned.
     */
    fun findOrAddChild(regex: String, sensitive: Boolean, plusCase: Boolean, terminal: Boolean): Node {
        val existingChild = children.find { it.match(regex) }
        if (existingChild != null) {
            return existingChild
        }

        val newNode = Node(regex, sensitive, plusCase, terminal)
        this.addChild(newNode)
        return newNode
    }
}
