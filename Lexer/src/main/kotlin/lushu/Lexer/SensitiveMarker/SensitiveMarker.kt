package lushu.Lexer.SensitiveMarker

import org.slf4j.LoggerFactory
import kotlin.text.Regex

class SensitiveMarker(val tokenDelimeter: String) {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    private val sensitiveRegex = Regex("<(\\p{Alnum}+)>(.+)</\\1>")

    fun findSensitiveTokens(ln: String): Pair<List<Int>?, String> {
        logger.debug("In findSensitiveTokens. Received line: $ln")

        // Remove cases where the delimeter is given in sequence.
        val moreThanOneDelimeter = Regex("($tokenDelimeter){2,}")
        val cleanLine = moreThanOneDelimeter
            .replace(ln, tokenDelimeter)
            .trim({ it.toString() == tokenDelimeter })

        var matches = sensitiveRegex.findAll(cleanLine)
        if (matches.count() < 1) {
            return Pair(null, cleanLine)
        }

        // Keep a map of <token starting position> -> <token index in given
        // line>
        var tokenPositionToIndex = mutableMapOf<Int, Int>()
        var tokenPositionToIndexPointer = 0
        var indexInLine = 0
        val splitLine = cleanLine.split(tokenDelimeter)
        splitLine.forEach { token ->
            if (token == "") {
                return@forEach
            }
            tokenPositionToIndex[tokenPositionToIndexPointer] = indexInLine
            indexInLine++
            tokenPositionToIndexPointer += token.length + tokenDelimeter.length
        }
        val lastWordIndex = indexInLine - 1
        logger.debug("Token position to index map: $tokenPositionToIndex")

        var matchIndexes = mutableListOf<Int>()
        var nonMarkedLine = ""
        var nonMarkedLineIdx = 0
        matches.iterator().forEach { match ->
            val start = match.range.start
            val end = match.range.endInclusive + tokenDelimeter.length

            // Add the index of each token in the matched sensitive string
            var matchedWordIndexInLine = tokenPositionToIndex.getValue(start)
            var rlimWordIndexInLine: Int
            if (end + tokenDelimeter.length >= cleanLine.length) {
                rlimWordIndexInLine =
                    lastWordIndex + tokenDelimeter.length
            } else {
                rlimWordIndexInLine =
                    tokenPositionToIndex.getValue(end + tokenDelimeter.length)
            }
            while (matchedWordIndexInLine < rlimWordIndexInLine) {
                matchIndexes += matchedWordIndexInLine
                matchedWordIndexInLine++
            }
            logger.debug("Matched word index in line: $matchedWordIndexInLine")

            // TODO: try to refactor this -aholmquist 2022-10-30
            //
            // Add pieces of line without the markdown characters, so that
            // original (non-marked) input can be easily processed downstream.
            //
            // Before
            nonMarkedLine += cleanLine.substring(nonMarkedLineIdx, start)
            // Middle
            val (_, middleValue) = match.destructured
            nonMarkedLine += middleValue
            // Update index
            nonMarkedLineIdx = end
            // Don't forget token delimeter, if this is not the last word.
            if (matchedWordIndexInLine < lastWordIndex) {
                nonMarkedLine += tokenDelimeter
                nonMarkedLineIdx += tokenDelimeter.length
            }
            logger.debug("Non marked line: $nonMarkedLine")
        }
        // Add rest of the line
        if (nonMarkedLineIdx < cleanLine.length) {
            nonMarkedLine += cleanLine.substring(nonMarkedLineIdx)
        }

        return Pair(matchIndexes, nonMarkedLine)
    }
}
