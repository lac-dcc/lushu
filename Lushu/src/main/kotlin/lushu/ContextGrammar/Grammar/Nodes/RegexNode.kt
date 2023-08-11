package lushu.ContextGrammar.Grammar.Nodes

interface RegexNode {

    val regex: String

    /**
     * Retrieves the regular expression used for pattern matching.
     *
     * @return The regular expression string.
     */
    fun getRegex() : String

    /**
     * Combines two regex nodes.
     *
     * This function checks if the regex of the 'acc' node matches the regex of the 'node' parameter.
     * If the match is successful, the 'acc' node is returned. Otherwise, the 'acc' and 'node' are merged.
     *
     * @param acc The first RegexNode to be considered for the meeting condition.
     * @param node The second RegexNode to be compared against the 'acc' node's regex.
     * @return The resulting RegexNode after applying the merger.
     */
    fun meetRegex(acc: RegexNode, n2: RegexNode): RegexNode
}