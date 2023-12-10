package lushu.ContextGrammar.MapGrammar

import lushu.Merger.Lattice.Node.*
import lushu.Merger.Lattice.Node.MergerS
import lushu.Merger.Merger.Merger

class ContextMap(
    val dsl: DSL = DSL(),
    val merger: Merger,
) {
    data class PivotData(
        val openingRef: Token,
        val func: String,
    )

    // openingMap contains the first tags of contexts. For instance, in '<c>
    // BEGIN <*>bla</*> END </c>', BEGIN is an opening token.
    private val openingMap: MutableMap<Token, Int> = mutableMapOf()

    // closingMap contains the last tags of contexts. For instance, in '<c> BEGIN
    // <*>bla</*> END </c>', END is an closing token.
    private val closingMap: MutableMap<Token, Token> = mutableMapOf()

    // pivotMap stores the string we are searching for. For instance, in '<c> BEGIN
    // <*>bla</*> END </c>', 'bla' is a pivot token.
    private val pivotMap: MutableMap<Token, ContextMap.PivotData> = mutableMapOf()

    private inline fun <reified V> insert(map: MutableMap<Token, V>, key: Token, value: V) {
        map[key] = value
    }

    private inline fun <reified V> inMap(map: MutableMap<Token, V>, token: Token): Token? {
        for (key in map.keys) {
            if (key.match(token.tokens)) {
                return key
            }
        }
        return null
    }

    private inline fun <reified V> inMap(map: MutableMap<Token, V>, word: List<Node>): Token? {
        for (key in map.keys) {
            if (key.match(word)) {
                return key
            }
        }
        return null
    }

    private inline fun <reified V> inMap(map: MutableMap<Token, V>, word: String): Token? {
        val tk = MergerS.merger().tokensFromString(word)
        return inMap(map, tk)
    }

    private fun addAccMap(token: Token, value: Int) {
        val oldValue = openingMap[token] ?: 0
        this.openingMap[token] = oldValue + value
    }

    fun match(word: String) {
        val wordTokens = merger.tokensFromString(word)

        val openingFound = inMap(openingMap, wordTokens)
        if (openingFound != null) {
            addAccMap(openingFound, 1)
        }

        val closingFound = inMap(closingMap, wordTokens)
        if (closingFound != null) {
            val openingToken = closingMap[closingFound]
            if (openingToken != null) {
                addAccMap(openingToken, -1)
            }
        }

        pivotMap.forEach { pivot ->
            if (pivot.component1().match(wordTokens)) {
                val openingReference = pivot.component2().openingRef
                val value = openingMap[openingReference]
                if (value != null && value >= 1) {
                }
            }
        }
    }

    private inline fun <reified V> insert(map: MutableMap<Token, V>, key: String, value: V) {
        insert(map, toToken(key), value)
    }

    private fun insertPivot(pivots: List<String>, openingToken: Token) {
        pivots.forEach { pivot ->
            val func = dsl.getLambdaFunction(pivot)
            insert(pivotMap, pivot, PivotData(openingToken, func))
        }
    }

    private fun string2list(string: String): List<String> {
        val woNewline = string.split(Grammar.newlineDelim).joinToString(Grammar.spaceDelim)
        return woNewline.split(Grammar.spaceDelim)
    }

    private fun insert(context: String) {
        val contextTokens = string2list(context)

        if (contextTokens.size < Grammar.minElems) {
            println("Warning: Insufficient number of elements provided.")
            return
        }

        val token = toToken(contextTokens.first())
        val res = findEquivalent(openingMap, token)
        val openingToken = res.first
        if (!res.second) {
            insert(openingMap, openingToken, Grammar.noOccurrences)
        }
        insert(closingMap, contextTokens.last(), openingToken)
        insertPivot(contextTokens.subList(1, contextTokens.size - 1), openingToken)
    }

    fun insertContext(context: String) {
        val contexts = dsl.extractContext(context)
        contexts.forEach { insert(it) }
    }

    private inline fun <reified V> findEquivalent(map: MutableMap<Token, V>, token: Token): Pair<Token, Boolean> {
        map.forEach { element ->
            if (element.key == token) {
                return Pair(element.key, true)
            }
        }
        return Pair(token, false)
    }

    private fun toToken(word: String): Token {
        val isMergeable = dsl.isMergeable(word)
        val woTags = dsl.removeAllTags(word)
        if (isMergeable) {
            return MergeableToken(woTags)
        }
        return NonMergeableToken(woTags)
    }

    companion object {
        val minElems = 3
        val noOccurrences = 0
        val spaceDelim = " "
        val newlineDelim = "\n"

        val blankRegex = Regex("""\S+""")
    }
}
