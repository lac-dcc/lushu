package lushu.ContextGrammar.Grammar

import lushu.Merger.Lattice.Node.MergeableToken
import lushu.Merger.Lattice.Node.NonMergeableToken
import lushu.Merger.Lattice.Node.Token

enum class Tags(val tagName: String) {
    CONTEXT("c"),
    LATTICE("l"),
    KLEENE("*"),
    ACTION("a"),
}

class MapGrammar(
    val tags: List<String> = Tags.values().map { it.tagName }.map { "<$it>" },
) {
    data class PivotData(
        val opening_reference: Token,
        val lambda_funcion: String,
    )

    private val opening_map: MutableMap<Token, Int> = mutableMapOf()
    private val closing_map: MutableMap<Token, Token> = mutableMapOf()
    private val map_pivot: MutableMap<Token, PivotData> = mutableMapOf()

    private fun string2list(string: String): List<String> {
        val lines = string.split("\n")
        val wo_newline = lines.joinToString(" ")
        return wo_newline.split(" ")
    }

    private fun isMergeable(string: String): Boolean {
        return string.contains("<l>") || string.contains("</l>")
    }

    private fun opening2CloserTag(openingTag: String): String {
        val tagName = openingTag.removeSurrounding("<", ">")
        return "</$tagName>"
    }

    private fun removeTags(word: String, tagSet: List<String>): String {
        return tagSet.fold(word) { acc, tag -> acc.replace(tag, "") }
    }

    private fun openingTags2ClosingTags(tags: List<String>): List<String> {
        return tags.map { opening2CloserTag(it) }.toList()
    }

    private fun removeAllTags(word: String): String {
        return removeTags(removeTags(word, tags.toList()), openingTags2ClosingTags(tags))
    }

    private fun toToken(word: String): Token {
        val isMergeable = isMergeable(word)
        val wo_tags = removeAllTags(word)

        if (isMergeable) {
            return MergeableToken(wo_tags)
        } else {
            return NonMergeableToken(wo_tags)
        }
    }

    private inline fun <reified V> inMap(map: MutableMap<Token, V>, token: Token): Token? {
        for (key in map.keys) {
            if (key.match(token.tokens)) {
                return key
            }
        }
        return null
    }

    private fun addAccMap(token: Token, value: Int) {
        val oldValue = opening_map[token] ?: 0
        this.opening_map[token] = oldValue + value
    }

    private fun match(word: String) {
        val token = toToken(word)

        val openingFound = inMap(opening_map, token)
        if (openingFound != null) {
            addAccMap(openingFound, 1)
            println("match opening $word")
        }

        val closingFound = inMap(closing_map, token)
        if (closingFound != null) {
            val openingToken = closing_map[closingFound]
            if (openingToken != null) {
                addAccMap(openingToken, -1)
            }
            println("match closing $word")
        }

        map_pivot.forEach { pivot ->
            if (pivot.component1().match(token.tokens)) {
                val openingReference = pivot.component2().opening_reference
                val value = opening_map[openingReference]
                if (value != null && value >= 1) {
                    println("match $word")
                    println("acc ${opening_map[openingReference]}")
                }
            }
        }
    }

    private fun streamString(input: String) {
        val wordRegex = "\\S+".toRegex()

        wordRegex.findAll(input).forEach { matchResult ->
            val word = matchResult.value
            match(word)
        }
    }

    fun consume(input: String) {
        streamString(input)
    }

    private fun extractContext(input: String): List<String> {
        val regex = Regex("<c>(.*?)</c>")
        val matcher = regex.findAll(input)
        val substrings = matcher.map { it.groupValues[1] }.toList()
        return substrings
    }

    private fun getLambdaFunction(input: String): String {
        val regex = Regex(".*<a (\\w+)>.+</a>.*")
        val matchResult = regex.find(input)

        val res = matchResult?.groups?.get(1)?.value
        if (res.isNullOrBlank()) {
            return "println"
        }
        return res
    }

    private inline fun <reified V> insert(map: MutableMap<Token, V>, key: Token, value: V) {
        map[key] = value
    }

    private inline fun <reified V> insert(map: MutableMap<Token, V>, key: String, value: V) {
        insert(map, toToken(key), value)
    }

    private fun insertPivot(pivots: List<String>, openingToken: Token) {
        pivots.forEach { pivot ->
            val lambFun = getLambdaFunction(pivot)
            insert(map_pivot, pivot, PivotData(openingToken, lambFun))
        }
    }

    private fun insertContext(context: String) {
        val contextTokens = string2list(context)

        if (contextTokens.size < min_elements) {
            println("Warning: Insufficient number of elements provided.")
            return
        }

        val openingToken = toToken(contextTokens.first())
        insert(opening_map, openingToken, no_occurrences)
        insert(closing_map, contextTokens.last(), openingToken)
        insertPivot(contextTokens.subList(1, contextTokens.size - 1), openingToken)
    }

    fun addContext(contextInput: String?) {
        if (contextInput.isNullOrBlank()) {
            return
        }
        val contexts = extractContext(contextInput)
        contexts.forEach { insertContext(it) }
    }

    companion object {
        val min_elements = 3
        val no_occurrences = 0
        val space_delimiter = " "
        val newline_delimiter = "\n"
    }
}
