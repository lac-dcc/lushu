package lushu.ContextGrammar.MapGrammar

import lushu.ContextGrammar.Grammar.TagNames

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
        return string.contains("<l>") || string.contains("</l>")
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

    fun removeAllTags(word: String): String {
        return removeTags(removeTags(word, tags.toList()), openingTags2ClosingTags(tags))
    }
}
