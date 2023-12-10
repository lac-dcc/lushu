package lushu.ContextGrammar.Grammar

import lushu.Merger.Lattice.Node.GrammarNode

class Rules(private val root: GrammarNode = GrammarNode()) {

    private val terminalNode: GrammarNode = GrammarNode()
    private val dsl: DSL = DSL()

    /**
     * Checks if the word ends with the log separator or if there is a matching child node in the current node's children.
     *
     * @param word The word to be checked.
     * @param current The current node.
     * @return true if the word ends with the log separator or if there is a matching child node, false otherwise.
     *
     * Example:
     * Input: word = "example", current = GrammarNode(label = "root", children = [GrammarNode(label = "host"), GrammarNode(label = "example"), GrammarNode(label = "ip")])
     * Output: true
     */
    fun isEndingStarCase(word: String, current: GrammarNode?): Boolean {
        return word.endsWith(logSeparator) ||
            (current != null && current.getChildren().any { it.match(word) }) ||
            (current != null && current.isTerminal())
    }

    /**
     * Matches the star case in a list of input tokens, starting from a given index,
     * using a mutable copy of the tokens and a current node in a tree structure.
     *
     * @param inputTokens The list of input tokens.
     * @param mutableTokens A mutable copy of the input tokens.
     * @param index The starting index for matching.
     * @param current The current node in the trie structure.
     * @return A pair containing the updated index and the matched node, or null if no match is found.
     *
     * Example:
     * Let a context "name: Emily is a new user" where Emily can be repeatable.
     * Input: inputTokens = mutableListOf("name:", "Emily", "Emily", "Emily", "is", "a", "new", "user")
     *        mutableTokens = mutableListOf("name:", "Emily", "Emily", "Emily", "is", "a", "new", "user")
     *        index = 1
     *        current = GrammarNode(label = "Emily", children = [GrammarNode(label = "is")])
     * Output: (4, GrammarNode(label = "is"))
     */
    fun starCaseMatcher(
        inputTokens: MutableList<String>,
        mutableTokens: MutableList<String>,
        index: Int,
        current: GrammarNode?,
    ): Pair<Int, GrammarNode?> {
        when {
            // don't match
            (index >= inputTokens.size) -> {
                return Pair(noMatchFound, null)
            }

            // don't match
            current == null -> {
                return Pair(noMatchFound, null)
            }

            // recursive match
            current.match(mutableTokens[index]) -> {
                if (current.isSensitive()) {
                    mutableTokens[index] = asteriskSymbol.repeat(mutableTokens[index].length)
                }

                return starCaseMatcher(inputTokens, mutableTokens, index + 1, current)
            }

            // if the current word is the end of the match.
            isEndingStarCase(mutableTokens[index], current) -> {
                inputTokens.clear()
                inputTokens.addAll(mutableTokens)
                val endStarCaseNode = matcher(current, inputTokens[index])

                return Pair(index, endStarCaseNode)
            }

            // fail match
            else -> {
                return Pair(noMatchFound, null)
            }
        }
    }

    /**
     * Match a given word against the children nodes of a current node in a tree structure.
     *
     * @param current The current node being evaluated.
     * @param word The word to be matched against the children nodes.
     * @return The matched node, if found; the terminalNode flag, if the node doesn't have children; otherwise, null.
     *
     * Example:
     * Input: current = GrammarNode(label = "root", children = [GrammarNode(label = "host"), GrammarNode(label = "warning"), GrammarNode(label = "ip")]), word = "host"
     * Output: GrammarNode(label = "host")
     */
    fun matcher(current: GrammarNode?, word: String): GrammarNode? {
        when {
            // not found
            (current == null) -> return null

            // end of the context
            (current.getChildren().isNullOrEmpty()) -> {
                if (current.isTerminal()) {
                    return terminalNode
                } else {
                    return null
                }
            }

            // searching for the respective child
            else -> {
                val childMatch = current.getChildren()?.find { child ->
                    child.match(word)
                }

                if (childMatch != null) {
                    return childMatch
                } else if (current.isTerminal()) {
                    return terminalNode
                } else {
                    return null
                }
            }
        }
    }

    /**
     * Matches input tokens against a given pattern represented by a tree of nodes.
     *
     * @param inputTokens The list of input tokens to be matched.
     * @param mutableTokens The mutable list of tokens used for matching and modification.
     * @param index The current index in the inputTokens list.
     * @param current The current node being evaluated.
     * @return The number of tokens matched successfully.
     *
     * Example:
     * Let a context "host ip: 123.123.123.123".
     * Input:inputTokens = ["server", "host", "ip:",  "123.123.123.123", "is", "online"]
     *       mutableTokens = ["server", "host", "ip:",  "123.123.123.123", "is", "online"]
     *       index = 1
     *       current = rootNode
     * Output: 3
     */
    fun matchTokensAgainstPatternContext(
        inputTokens: MutableList<String>,
        mutableTokens: MutableList<String>,
        index: Int,
        current: GrammarNode?,
    ): Int {
        when {
            current == null -> return noMatchFound

            index >= inputTokens.size -> {
                if (current.getChildren().isNullOrEmpty()) {
                    inputTokens.clear()
                    inputTokens.addAll(mutableTokens)
                    return (inputTokens.size - index - 1)
                }
                return noMatchFound
            }

            current == terminalNode -> {
                inputTokens.clear()
                inputTokens.addAll(mutableTokens)
                return (inputTokens.size - index - 1)
            }

            current.isStar() -> {
                val (nextIndex: Int, nextNode: GrammarNode?) = starCaseMatcher(
                    inputTokens,
                    mutableTokens,
                    index,
                    current,
                )

                if ((nextNode != null) && (nextNode.isSensitive())) {
                    mutableTokens[nextIndex] = asteriskSymbol.repeat(mutableTokens[nextIndex].length)
                }

                return matchTokensAgainstPatternContext(inputTokens, mutableTokens, nextIndex + 1, nextNode)
            }

            else -> {
                val nextNode = matcher(current, inputTokens[index])

                if ((nextNode != null) && (nextNode.isSensitive())) {
                    mutableTokens[index] = asteriskSymbol.repeat(mutableTokens[index].length)
                }

                return matchTokensAgainstPatternContext(inputTokens, mutableTokens, index + 1, nextNode)
            }
        }
    }

    /**
     * Finds the matching indices of tokens in the input list based on a list of regular expressions.
     *
     * @param inputTokens The list of tokens to search for matches.
     * @param tokensList The list of regular expressions to match against the tokens.
     * @return A list of unique and sorted indices of the matching tokens.
     *
     * Example:
     * Input: ["error", "warning", "exception", "success", "invalid"], ["e.", "s.", "c."]
     * Output: [0, 2, 3]
     */
    fun findMatchingIndex(inputTokens: List<String>, tokensList: List<GrammarNode>): List<Int> {
        val matchingIndex = tokensList.flatMap { grammarNode ->
            inputTokens.mapIndexedNotNull { index, word ->
                val matcher = grammarNode.match(word)
                if (matcher) index else null
            }
        }
        return matchingIndex.distinct().sorted()
    }

    /**
     * Converts a list of tokens into cipher tokens.
     * This function iterates over the input list and checks if it contains any text patterns that need to be encrypted.
     * The resulting cipher tokens are returned as a list.
     *
     * @param inputTokens The list of input tokens to be converted into cipher tokens.
     * @return The list of cipher tokens generated from the input tokens.
     *
     * Example:
     * Let a context "host ip: 123.123.123.123" where 123.123.123.123 needs to be encrypted.
     * Input: ["server", "host", "ip:", "123.123.123.123", "is", "online"]
     * Output: ["server", "host", "ip:",  "***************", "is", "online"]
     */
    fun tokens2CipherTokens(inputTokens: List<String>): List<String> {
        val tokensList: List<GrammarNode> = root.getChildren()
        val matchingIndex: List<Int> = findMatchingIndex(inputTokens, tokensList)

        var i = defaultMinimalIndex

        val cipherTokens = inputTokens.toMutableList()

        matchingIndex.forEach { index ->
            if (i <= index) {
                i = matchTokensAgainstPatternContext(cipherTokens, cipherTokens.toMutableList(), index, root)
            }
        }

        return cipherTokens
    }

    /**
     * Adds rules based on the given context rule by recursively processing each word in the context rule.
     *
     * @param contextRule The mutable list of strings representing the context rule.
     * @param current The current node in the tree structure. Defaults to the root node.
     *
     * - Example:
     * Input: mutableListOf("This", "<m>is", "an</m>", "<s>example</s>", "sentence.")
     */
    fun addContextRule(contextRule: MutableList<String>, current: GrammarNode? = root) {
        if (contextRule.isNullOrEmpty()) {
            return
        }
        val firstWord = 0

        val (isCases, nextCases) = dsl.hasTags(contextRule[firstWord])

        val word = dsl.removeAllTagsFromWord(contextRule[firstWord])

        dsl.setIsCase(isCases)

        // true if it's the last word in the context
        val endOfContext: Boolean = (contextRule.size == 1)

        val updatedCurrent = current?.findOrAddChild(
            word,
            dsl.isSensitive(),
            dsl.isStar(),
            dsl.isNonMergeable(),
            endOfContext,
        )

        dsl.setIsCase(nextCases)

        contextRule.removeAt(firstWord)

        if (nextCases[starCase]) {
            addContextRule(contextRule, current)
        } else {
            addContextRule(contextRule, updatedCurrent)
        }
    }

    /**
     * Retrieves the context from the given words and adds rules based on the context.
     *
     * @param words The string containing the words from which the context is extracted.
     *
     * - Example:
     * Input: This is an example sentence <c>with context</c> and <c>another context</c>
     */
    fun addContextsFromWords(words: String?) {
        val contexts = dsl.extractContext(words ?: "")
        contexts.forEach { context -> addContextRule(context.split(" ").toMutableList()) }
    }

    fun contextToString(): String {
        return root.toString()
    }

    companion object {
        private val logSeparator = "\n"
        private val asteriskSymbol = "*"
        private val starCase = 2
        private val noMatchFound = 1
        private val defaultMinimalIndex = -1
    }
}
