package lushu.ContextGrammar.Grammar

enum class TagNames(val tagName: String) {
    CONTEXT("c"),
    SENSITIVE("s"),
    STAR("*"),
    NONMERGEABLE("t")
    //GENERICTOKEN("**")
}

class DSL(
    val isCase: MutableList<Boolean> = TagNames.values().map { false }.toMutableList(),
    val tags: List<String> = TagNames.values().map { it.tagName }.map { "<$it>" },
) {

    /**
     * Checks if the current case is a sensitive case.
     *
     * @return true if the current case is a sensitive case, false otherwise.
     */
    fun isSensitive(): Boolean {
        return isCase[TagNames.SENSITIVE.ordinal]
    }

    /**
     * Checks if the current case is a star case.
     *
     * @return true if the current case is a star case, false otherwise.
     */
    fun isStar(): Boolean {
        return isCase[TagNames.STAR.ordinal]
    }

    /**
     * Checks if the current case is a NONMERGEABLE case.
     *
     * @return true if the current case is a NONMERGEABLE case, false otherwise.
     */
    fun isNonMergeable(): Boolean {
        return isCase[TagNames.NONMERGEABLE.ordinal]
    }

    /**
     * Sets the isCase list with the values from the previous list.
     *
     * @param previous The list of boolean values to set the isCase list.
     */
    fun setIsCase(previous: List<Boolean>) {
        if (isCase.size != previous.size) {
            return
        }
        isCase.clear()
        isCase.addAll(previous)
    }

    /**
     * Removes tags from a given word by replacing occurrences of tags in the word with an empty string.
     *
     * @param word The word from which tags need to be removed.
     * @param tagSet The list of tags to be removed from the word.
     * @return The word with tags removed.
     *
     * Example:
     * Input: (word: <tag><tag1>example</tag1>; tagSet: ListOf("<tag>","<tag1>"))
     * Output: "example</tag1>"
     */
    fun removeTagsFromWord(word: String, tagSet: List<String>): String {
        return tagSet.fold(word) { acc, tag -> acc.replace(tag, "") }
    }

    /**
     * Removes all tags from a word by recursively removing opening and closing tags.
     *
     * @param word The word from which tags should be removed.
     * @return The word without any tags.
     *
     * - Example:
     * Input: "<tag><tag1>example</tag1>"
     * Output: "example"
     */
    fun removeAllTagsFromWord(word: String): String {
        return removeTagsFromWord(removeTagsFromWord(word, tags.toList()), openingTags2ClosingTags(tags))
    }

    /**
     * Converts an opening tag to its corresponding closing tag.
     *
     * @param openingTag The opening tag to convert.
     * @return The closing tag corresponding to the given opening tag.
     *
     * - Example:
     * Input: "<tag>"
     * Output: "</tag>"
     */
    fun opening2CloserTag(openingTag: String): String {
        val tagName = openingTag.removeSurrounding("<", ">")
        return "</$tagName>"
    }

    /**
     * Converts a list of opening tags to their corresponding closing tags.
     *
     * @param tags The list of opening tags to convert.
     * @return A list of corresponding closing tags.

     * - Example:
     * Input: mutableListOf("<tag1>", "<tag2>", "<tag3>")
     * Output: ["</tag1>", "</tag2>", "</tag3>"]
     */
    fun openingTags2ClosingTags(tags: List<String>): List<String> {
        return tags.map { opening2CloserTag(it) }.toList()
    }

    /**
     * Checks if a word contains specific tags and returns two lists indicating the presence of opening tags and closing tags.
     *
     * @param word The word to check for tags.
     * @return A pair of lists where the first list represents the presence of opening tags in the word, and the second list represents the presence of closing tags.
     *
     * - Example:
     * Let tags = mutableListOf("<c>","<s>","<+>","<m>");
     * Input: "<s><m>example</s>";
     * Output: Pair([false, true, false, true], [false, true, false, false]).
     *
     * - Explanation:
     *  The word contains the tags "<s>" and "<+>", but not the tags "<c>" and "<m>", resulting in [false, true, true, false] for the presence of tags.
     *  The next case indicates that the closer tag "</s>" is present, but doesn't have any other closing tags, resulting in [false, true, false, false] for the next case.

     */
    fun hasTags(word: String): Pair<List<Boolean>, List<Boolean>> {
        val isCase = tags.map { word.contains(it) }
        val closerTags = openingTags2ClosingTags(tags)
        val nextCase =
            closerTags.map { !word.contains(it) }.zip(isCase).map { (next, current) -> if (next) current else next }

        return Pair(isCase, nextCase)
    }

    /**
     * Extracts context from the input string by finding all substrings enclosed within "<c>" and "</c>" tags.
     *
     * @param input string from which the context is extracted.
     * @return A list of extracted context substrings.
     *
     * Example:
     * Input: "This is <c>some</c> example <c>text</c>."
     * Output: ["some", "text"]
     **/
    fun extractContext(input: String): List<String> {
        val regex = Regex("$contextOpeningTag(.*?)$contextCloserTag")
        val matches = regex.findAll(input)
        val substrings = matches.map { it.groupValues[1] }.toList()
        return substrings
    }

    companion object {
        private val sensitiveCase = 1
        private val starCase = 2
        private val nonMergeableCase = 3
        private val contextOpeningTag = "<c>"
        private val contextCloserTag = "</c>"
    }
}
