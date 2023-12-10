package lushu.ContextGrammar.MapGrammar

enum class Tags(val tagName: String) {
    CONTEXT("c"),
    LATTICE("l"),
    KLEENE("*"),
    ACTION("a"),
}

class DSL(
    val tags: List<String> = Tags.values().map { it.tagName }.map { "<$it>" },
) {
    fun isMergeable(string: String): Boolean {
        return string.contains(Tags.LATTICE.tagName) || string.contains(Tags.LATTICE.tagName)
    }

    fun isStarCase(string: String): Boolean {
        return string.contains(Tags.KLEENE.tagName) || string.contains(Tags.KLEENE.tagName)
    }

    private fun opening2CloserTag(openingTag: String): String {
        val tagName = openingTag.removeSurrounding("<", ">")
        return "</$tagName>"
    }

    fun removeTags(word: String, tagSet: List<String>): String {
        return tagSet.fold(word) { acc, tag -> acc.replace(tag, "") }
    }

    fun openingTags2ClosingTags(tags: List<String>): List<String> {
        return tags.map { opening2CloserTag(it) }.toList()
    }

    fun removeActionTag(word: String): String {
        val regex = Regex("""<a=[^>]+>|</a>""")
        return word.replace(regex, "")
    }

    fun removeAllTags(word: String): String {
        val wordWT = removeTags(removeTags(word, tags.toList()), openingTags2ClosingTags(tags))
        return removeActionTag(wordWT)
    }

    fun extractContext(input: String): List<String> {
        val matcher = Grammar.contextRegex.findAll(input)
        val substrings = matcher.map { it.groupValues[1] }.toList()
        return substrings
    }

    fun getLambdaFunction(input: String): String {
        val matchResult = actionRegex.find(input)

        val res = matchResult?.groups?.get(1)?.value
        if (res.isNullOrBlank()) {
            return withoutAction
        }
        return res
    }

    companion object {
        val withoutAction = ""
        val actionRegex = Regex(".*<a=(\\w+)>.+</a>.*")
        val contextRegex = Regex("""<c>(.*?)</c>""")
    }
}
