package lushu.ContextGrammar.Grammar

class Node(
    private val regex: String = "",
    private val sensitive: Boolean = false,
    private val star: Boolean= false,
    private val terminal: Boolean = false,
    private val children: MutableList<Node> = mutableListOf()) {

    fun getRegex(): String{
        return this.regex
    }

    fun getChildren(): MutableList<Node>{
        return this.children
    }

    /**
     * Checks if the node is sensitive.
     * @return true if the current node is sensitive, false otherwise.
     */
    fun isSensitive(): Boolean{
        return this.sensitive
    }

    /**
     * Checks if the node is star type.
     * @return true if the current node is a star type, false otherwise.
     */
    fun isPlus(): Boolean{
        return this.star
    }

    /**
     * Checks if the node is terminal.
     * @return true if the current node is a terminal, false otherwise.
     */
    fun isTerminal(): Boolean{
        return this.terminal
    }

    /**
     * Determines if the given word matches the regular expression defined by the current instance.
     * @param word to be matched against the regular expression.
     * @return true if the word matches the regular expression, false otherwise.
     */
    fun match(word: String): Boolean{
        return Regex(this.getRegex()).matches(word)
    }

    /**
     * Adds a child node to the current node based on the specified parameters.
     * @param r The rule to match against the child nodes.
     * @param s Indicates whether the child node is sensitive.
     * @param pc Indicates whether the child node is plus case.
     * @param npc Indicates whether the next word is plus case too.
     * @param m Indicates whether the child node is mergeable.
     * @return The added child node that matches the rule, or the last child node if none match.
     */
    fun addChild(r: String, s: Boolean, pc: Boolean, npc: Boolean, m: Boolean): Node{
        this.getChildren().forEach{
            if(it.match(r))
                return it
        }
        this.getChildren().add(Node(r, s, pc, m))

        if(pc && npc)
            return this

        return this.getChildren().last()
    }
}