package lushu.ContextGrammar.Grammar

import java.util.regex.Pattern
class Rules(private val root: Node = Node()) {

    private val terminalNode: Node = Node()
    private val dsl: DSL = DSL()

    /**
     * Checks if the word ends with the log separator or if there is a matching child node in the current node's children.
     * @param word The word to be checked.
     * @param current The current node.
     * @return true if the word ends with the log separator or if there is a matching child node, false otherwise.
     * Example:
     * Input: word = "example", current = Node(label = "root", children = [Node(label = "host"), Node(label = "example"), Node(label = "ip")])
     * Output: true
     */
    private fun isEndingPlusCase(word: String, current: Node?): Boolean{
        return word.endsWith(logSeparator) ||
                (current != null && current.getChildren().any { it.match(word) })
    }

    /**
     * Matches the plus case in a list of input tokens, starting from a given index,
     * using a mutable copy of the tokens and a current node in a tree structure.
     * @param inputTokens The list of input tokens.
     * @param mutableTokens A mutable copy of the input tokens.
     * @param index The starting index for matching.
     * @param current The current node in the trie structure.
     * @return A pair containing the updated index and the matched node, or null if no match is found.
     * Example:
     * Let a context "name: Emily is a new user" where Emily can be repeatable.
     * Input: inputTokens = mutableListOf("name:", "Emily", "Emily", "Emily", "is", "a", "new", "user")
     *        mutableTokens = mutableListOf("name:", "Emily", "Emily", "Emily", "is", "a", "new", "user")
     *        index = 1
     *        current = Node(label = "Emily", children = [Node(label = "is")])
     * Output: (4, Node(label = "is"))
     */
    private fun plusCaseMatcher(inputTokens: MutableList<String>, mutableTokens: MutableList<String>, index: Int, current: Node?): Pair<Int, Node?> {
        if(current == null)
            return Pair(1, null)

        if (isEndingPlusCase(mutableTokens[index], current)) {
            inputTokens.clear()
            inputTokens.addAll(mutableTokens)
            val endPlusCaseNode = matcher(current, inputTokens[index])

            return Pair(index, endPlusCaseNode)
        }

        if (current.match(mutableTokens[index])) {
            if(current.isSensitive())
                mutableTokens[index] = "*".repeat(mutableTokens[index].length)

            return plusCaseMatcher(inputTokens, mutableTokens, index + 1, current)
        }

        return Pair(1, null)
    }

    /**
     * match a given word against the children nodes of a current node in a tree structure.
     * @param current The current node being evaluated.
     * @param word The word to be matched against the children nodes.
     * @return The matched node, if found; the terminalNode flag, if the node doesn't have children; otherwise, null.
     * Example:
     * Input: current = Node(label = "root", children = [Node(label = "host"), Node(label = "warning"), Node(label = "ip")]), word = "host"
     * Output: Node(label = "host")
     */
    private fun matcher(current: Node?, word: String): Node?{
        if(current == null)
            return null

        if(current!!.getChildren().isNullOrEmpty())
            return terminalNode

        return current?.getChildren()?.find { child ->
            child.match(word)
        }
    }

    /**
     * Matches input tokens against a given pattern represented by a tree of nodes.
     * @param inputTokens The list of input tokens to be matched.
     * @param mutableTokens The mutable list of tokens used for matching and modification.
     * @param index The current index in the inputTokens list.
     * @param current The current node being evaluated.
     * @return The number of tokens matched successfully.
     * Example:
     * Let a context "host ip: 123.123.123.123".
     * Input:inputTokens = ["server", "host", "ip:",  "123.123.123.123", "is", "online"]
     *       mutableTokens = ["server", "host", "ip:",  "123.123.123.123", "is", "online"]
     *       index = 1
     *       current = rootNode
     * Output: 3
     */
    private fun matchTokensAgainstPatternContext(inputTokens: MutableList<String>, mutableTokens: MutableList<String>, index: Int, current: Node?): Int {
        if (current == null) {
            return 1
        }
        if (index >= inputTokens.size) {
            if(current.getChildren().isNullOrEmpty()){
                inputTokens.clear()
                inputTokens.addAll(mutableTokens)
                return (inputTokens.size - index - 1)
            }
            return 1
        }

        if (current == terminalNode) {
            inputTokens.clear()
            inputTokens.addAll(mutableTokens)
            return (inputTokens.size - index - 1)
        }
        if (current.isPlus()) {
            val (nextIndex: Int, nextNode: Node?) = plusCaseMatcher(inputTokens, mutableTokens, index, current)

            if ((nextNode != null) && (nextNode.isSensitive()))
                mutableTokens[nextIndex] = "*".repeat(mutableTokens[nextIndex].length)

            return matchTokensAgainstPatternContext(inputTokens, mutableTokens, nextIndex, nextNode)
        }
        else {
            val nextNode = matcher(current, inputTokens[index])

            if ((nextNode != null) && (nextNode.isSensitive()))
                mutableTokens[index] = "*".repeat(mutableTokens[index].length)

            return matchTokensAgainstPatternContext(inputTokens, mutableTokens, index + 1, nextNode)
        }
    }

    /**
     * Finds the matching indices of tokens in the input list based on a list of regular expressions.
     * @param inputTokens The list of tokens to search for matches.
     * @param regexList The list of regular expressions to match against the tokens.
     * @return A list of unique and sorted indices of the matching tokens.
     * Example:
     * Input: ["error", "warning", "exception", "success", "invalid"], ["e.", "s.", "c."]
     * Output: [0, 2, 3]
     */
    private fun findMatchingIndex(inputTokens: List<String>, regexList: List<String>): List<Int> {

        val matchingIndex = regexList.flatMap { regex ->
            val pattern = Pattern.compile(regex)
            inputTokens.mapIndexedNotNull { index, word ->
                val matcher = pattern.matcher(word)
                if (matcher.find()) index else null
            }
        }
        return matchingIndex.distinct().sorted()
    }

    /**
     * Converts a list of tokens into cipher tokens.
     * This function iterates over the input list and checks if it contains any text patterns that need to be encrypted.
     * The resulting cipher tokens are returned as a list.
     * @param inputTokens The list of input tokens to be converted into cipher tokens.
     * @return The list of cipher tokens generated from the input tokens.
     * Example:
     * Let a context "host ip: 123.123.123.123" where 123.123.123.123 needs to be encrypted.
     * Input: ["server", "host", "ip:",  "123.123.123.123", "is", "online"]
     * Output: ["server", "host", "ip:",  "***************", "is", "online"]
     */
    fun tokens2CipherTokens(inputTokens: List<String>): List<String>{
        val regexList: List<String> = root.getChildren().map { child -> child.getRegex() }
        val matchingIndex: List<Int> = findMatchingIndex(inputTokens, regexList)

        var i = -1

        val cipherTokens = inputTokens.toMutableList()

        matchingIndex.forEach { index ->
            if(i <= index){
                i = matchTokensAgainstPatternContext(cipherTokens, inputTokens.toMutableList(), index, root)}
        }

        return cipherTokens
    }

    /**
     * Adds rules based on the given context rule by recursively processing each word in the context rule.
     * @param contextRule The mutable list of strings representing the context rule.
     * @param current The current node in the tree structure. Defaults to the root node.
     *
     * - Example:
     * Input: mutableListOf("This", "<t>is", "an</t>", "<s>example</s>", "sentence.")
     */
    private fun addContextToGrammar(contextRule: MutableList<String>, current: Node? = root) {
        if (contextRule.isNullOrEmpty()) {
            return
        }
        val firstWord = 0

        val (isCase, nextCase) = dsl.hasTags(contextRule[firstWord])

        val word = dsl.removeAllTagsFromWord(contextRule[firstWord])

        dsl.setIsCase(isCase)

        val updatedCurrent = current?.addChild(word, s = dsl.isSensitive(), pc = dsl.isPlus(), npc = nextCase[isStar], m = dsl.isTerminal())

        dsl.setIsCase(nextCase)

        contextRule.removeAt(firstWord)
        addContextToGrammar(contextRule, updatedCurrent)
    }

    /**
     * Retrieves the context from the given words and adds rules based on the context.
     * @param words The string containing the words from which the context is extracted.
     *
     * - Example:
     * Input: This is an example sentence <c>with context</c> and <c>another context</c>
     */
    fun getContext(words: String?){
        val contexts = dsl.extractContext(words ?: "")
        contexts.forEach { context -> addContextToGrammar(context.split(" ").toMutableList())}
    }

    companion object{
        private val logSeparator = "\n"
        private val isStar = 2
    }

}