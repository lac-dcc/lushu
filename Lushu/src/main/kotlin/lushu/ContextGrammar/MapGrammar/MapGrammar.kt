package lushu.ContextGrammar.MapGrammar

import lushu.Merger.Lattice.Node.MergeableToken
import lushu.Merger.Lattice.Node.MergerS
import lushu.Merger.Lattice.Node.Node
import lushu.Merger.Lattice.Node.NonMergeableToken
import lushu.Merger.Lattice.Node.Token
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader

class MapGrammar(
    val dsl: DSL = DSL(),
    var maxBlocks: Int = 0
) {
    data class PivotData(
        val openingRef: Token,
        val func: String
    )

    private val merger = MergerS.merger()

    private lateinit var outfile: BufferedWriter

    private val openingMap: MutableMap<Token, Int> = mutableMapOf()
    private val closingMap: MutableMap<Token, Token> = mutableMapOf()
    private val mapPivot: MutableMap<Token, PivotData> = mutableMapOf()

    private fun string2list(string: String): List<String> {
        val lines = string.split("\n")
        val wo_newline = lines.joinToString(" ")
        return wo_newline.split(" ")
    }

    private fun toToken(word: String): Token {
        val isMergeable = dsl.isMergeable(word)
        val wo_tags = dsl.removeAllTags(word)

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

    private fun match(word: String) {
        val inToken = merger.tokensFromString(word)
        val openingFound = inMap(openingMap, inToken)
        if (openingFound != null) {
            addAccMap(openingFound, 1)
        }

        val closingFound = inMap(closingMap, inToken)
        if (closingFound != null) {
            val openingToken = closingMap[closingFound]
            if (openingToken != null) {
                addAccMap(openingToken, -1)
            }
        }

        mapPivot.forEach { pivot ->
            if (pivot.component1().match(inToken)) {
                val openingReference = pivot.component2().openingRef
                val value = openingMap[openingReference]
                if (value != null && value > 1) {
                    maxBlocks += 1
                }
            }
        }
    }

    private fun streamString(input: String) {
        input.split(spaceDelim).forEach { word -> match(word) }
    }

    fun consume(input: String) {
        streamString(input)
    }

    fun consume(file: File, outfile: String): Int {
        FileReader(file).use { reader ->
            reader.forEachLine {
                consume(it)
            }
        }
        return maxBlocks
    }

    private fun extractContext(input: String): List<String> {
        val matcher = contextRegex.findAll(input)
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
            val func = getLambdaFunction(pivot)
            insert(mapPivot, pivot, PivotData(openingToken, func))
        }
    }

    private inline fun <reified V> findEquivalent(map: MutableMap<Token, V>, token: Token): Pair<Token, Boolean> {
        map.forEach { element ->
            if (element.key == token) {
                return Pair(element.key, true)
            }
        }
        return Pair(token, false)
    }

    private fun insertContext(context: String) {
        val contextTokens = string2list(context)

        if (contextTokens.size < minElems) {
            println("Warning: Insufficient number of elements provided.")
            return
        }

        val token = toToken(contextTokens.first())
        val res = findEquivalent(openingMap, token)
        val openingToken = res.first
        if (!res.second) {
            insert(openingMap, openingToken, noOccurrences)
        }
        insert(closingMap, contextTokens.last(), openingToken)
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
        val minElems = 3
        val noOccurrences = 0
        val spaceDelim = " "
        val newlineDelim = "\n"

        val contextRegex = Regex("""<c>(.*?)</c>""")
        val wordRegex = Regex("""\S+""")
    }
}
