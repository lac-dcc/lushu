package lushu.Lexer.SensitiveMarker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SensitiveMarkerTest {
    companion object {
        val marker = SensitiveMarker(" ")
    }

    @Test
    fun findSensitiveTokensEmptyLine() {
        val line = ""
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(null, "")
        assertEquals(expected, actual)
    }

    @Test
    fun findSensitiveTokensWithoutMarkdown() {
        val line = "Shaken, not stirred"
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(null, "Shaken, not stirred")
        assertEquals(expected, actual)
    }

    @Test
    fun findSensitiveTokensOneMarkedMiddle() {
        val line = "My name is <s>Bond,</s> James Bond"
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(
            listOf(3),
            "My name is Bond, James Bond"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findSensitiveTokensOneMarkedEnd() {
        val line = "My name is Bond, James <s>Bond</s>"
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(
            listOf(5),
            "My name is Bond, James Bond"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findSensitiveTokensTwoMarkedMiddle() {
        val line = "My name is <s>Bond, James</s> Bond"
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(
            listOf(3, 4),
            "My name is Bond, James Bond"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findSensitiveTokensTwoMarkedEnd() {
        val line = "My name is Bond, <s>James Bond</s>"
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(
            listOf(4, 5),
            "My name is Bond, James Bond"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findSensitiveTokensThreeMarkedEnd() {
        val line = "My name is <s>Bond, James Bond</s>"
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(
            listOf(3, 4, 5),
            "My name is Bond, James Bond"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findSensitiveTokensThreeMarkedEndWithTrailingSpace() {
        val line = "    My name is <s>Bond, James Bond</s>      "
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(
            listOf(3, 4, 5),
            "My name is Bond, James Bond"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findSensitiveTokensLotsOfSpaces() {
        val line = "    My     name     is   <s>Bond,    James    Bond</s>     "
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(
            listOf(3, 4, 5),
            "My name is Bond, James Bond"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun findSensitiveTokensMarkedWithDifferentTag() {
        val line = "My name is <sensitive>Bond, James Bond</sensitive>"
        val actual = marker.findSensitiveTokens(line)
        val expected = Pair(
            listOf(3, 4, 5),
            "My name is Bond, James Bond"
        )
        assertEquals(expected, actual)
    }
}
