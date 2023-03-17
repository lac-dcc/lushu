package lushu.Lexer.Regex

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// TODO: add tests to test Inference Machine properties (iterative, set-driven,
// etc) -aholmquist 2022-10-22
class CompressorTest {
    private val nf = NodeFactory()
    private val lattice = Lattice(testBaseNodes(), testDisjointNodes())
    private val compressor = Compressor(
        nf,
        lattice
    )

    @Test
    fun compressEmpty() {
        val ns1 = nf.buildBasicNodes("")
        val ns2 = nf.buildBasicNodes("")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = ""
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressOneAlpha() {
        val ns1 = nf.buildBasicNodes("")
        val ns2 = nf.buildBasicNodes("a")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[a]{1,1}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressOneNum() {
        val ns1 = nf.buildBasicNodes("")
        val ns2 = nf.buildBasicNodes("1")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[1]{1,1}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressManyChar() {
        val ns1 = nf.buildBasicNodes("")
        val ns2 = nf.buildBasicNodes("abcd")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[abcd]{4,4}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressManyNum() {
        val ns1 = nf.buildBasicNodes("")
        val ns2 = nf.buildBasicNodes("1234")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[1234]{4,4}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingSingletonEqual() {
        val ns1 = nf.buildBasicNodes("a")
        val ns2 = nf.buildBasicNodes("a")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[a]{1,1}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingSingletonDiff() {
        val ns1 = nf.buildBasicNodes("a")
        val ns2 = nf.buildBasicNodes("b")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[ab]{1,1}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingSingletonAlphaNum() {
        val ns1 = nf.buildBasicNodes("a")
        val ns2 = nf.buildBasicNodes("1")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = alnumLowerStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingMultipleRepeated() {
        val ns1 = nf.buildBasicNodes("aaaa")
        val ns2 = nf.buildBasicNodes("aaaa")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[a]{4,4}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingMultipleEqual() {
        val ns1 = nf.buildBasicNodes("abcd")
        val ns2 = nf.buildBasicNodes("abcd")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[abcd]{4,4}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingMultipleEqualAlphaAndNum() {
        val ns1 = nf.buildBasicNodes("abc12")
        val ns2 = nf.buildBasicNodes("abc12")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = alnumLowerStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingMultipleTwoDiffOneEqual() {
        val ns1 = nf.buildBasicNodes("cba")
        val ns2 = nf.buildBasicNodes("abc")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[abc]{3,3}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingMultipleAllDiff() {
        val ns1 = nf.buildBasicNodes("abc")
        val ns2 = nf.buildBasicNodes("efg")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[abcefg]{3,3}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingMultipleDifferentSizes() {
        val ns1 = nf.buildBasicNodes("abc")
        val ns2 = nf.buildBasicNodes("abcdef")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[abcdef]{3,6}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingMultipleDifferentSizesTwice() {
        val ns1 = nf.buildBasicNodes("abc")
        val ns2 = nf.buildBasicNodes("abcdef")
        val actual1 = compressor.compressNodes(ns1, ns2)
        val expected1 = "[abcdef]{3,6}"
        assertEquals(expected1, nf.buildString(actual1))

        val ns3 = nf.buildBasicNodes("abcdefghi")
        val actual2 = compressor.compressNodes(actual1, ns3)
        val expected2 = "[abcdefghi]{3,9}"
        assertEquals(expected2, nf.buildString(actual2))
    }

    @Test
    fun compressFromExistingPreviousIsLonger() {
        val ns1 = nf.buildBasicNodes("abcdef")
        val ns2 = nf.buildBasicNodes("abc")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[abcdef]{3,6}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressFromExistingMultipleAlphaNumDiff() {
        val ns1 = nf.buildBasicNodes("abc12")
        val ns2 = nf.buildBasicNodes("efg34")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = alnumLowerStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressTimestamp() {
        val ns1 = nf.buildBasicNodes("00:00:00")
        val ns2 = nf.buildBasicNodes("12:34:56")
        val actual = compressor.compressNodes(ns1, ns2)
        val expected = "[012]{2,2}[:]{1,1}[034]{2,2}[:]{1,1}[056]{2,2}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressTimestampTwice() {
        val ns1 = nf.buildBasicNodes("00:00:00")
        val ns2 = nf.buildBasicNodes("12:34:56")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[012]{2,2}[:]{1,1}[034]{2,2}[:]{1,1}[056]{2,2}"
        assertEquals(expected, nf.buildString(actual))

        val ns3 = nf.buildBasicNodes("12:34:56")
        actual = compressor.compressNodes(actual, ns3)
        // Regex should remain the same, given that we have already seen this
        // example in the past.
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressIP() {
        val ns1 = nf.buildBasicNodes("0.0.0.0")
        val ns2 = nf.buildBasicNodes("12.34.56.78")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[012]{1,2}[.]{1,1}[034]{1,2}[.]{1,1}[056]{1,2}[.]{1,1}[078]{1,2}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressSubIP() {
        val ns1 = nf.buildBasicNodes("1.2.3")
        val ns2 = nf.buildBasicNodes("123.456.789")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[123]{1,3}[.]{1,1}[2456]{1,3}[.]{1,1}[3789]{1,3}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressIPAndSubIPCommonNumbers() {
        val ns1 = nf.buildBasicNodes("123.456.789")
        val ns2 = nf.buildBasicNodes("12.34.78")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[123]{2,3}[.]{1,1}[3456]{2,3}[.]{1,1}[789]{2,3}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressIPAndSubIPCommonNumbersTwice() {
        val ns1 = nf.buildBasicNodes("123.456.789")
        val ns2 = nf.buildBasicNodes("12.34.78")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[123]{2,3}[.]{1,1}[3456]{2,3}[.]{1,1}[789]{2,3}"
        assertEquals(expected, nf.buildString(actual))

        // Shouldn't change
        actual = compressor.compressNodes(nf.parseNodes(expected), ns2)
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressAlphaAndPunctSamePlace() {
        val ns1 = nf.buildBasicNodes("123a")
        val ns2 = nf.buildBasicNodes("123:")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = dotStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressTimestampAndNumber() {
        val ns1 = nf.buildBasicNodes("00:00:00")
        val ns2 = nf.buildBasicNodes("123")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = dotStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressTimestampAndPartialTimestamp() {
        val ns1 = nf.buildBasicNodes("00:00:00")
        val ns2 = nf.buildBasicNodes("00:00")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = dotStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressTimestampAndPartialTimestampReverseOrder() {
        val ns1 = nf.buildBasicNodes("00:00")
        val ns2 = nf.buildBasicNodes("00:00:00")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = dotStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressPunctsDifferentSizes() {
        val ns1 = nf.buildBasicNodes("---")
        val ns2 = nf.buildBasicNodes("====")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[-=]{3,4}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressPunctsDifferentSizesReverseOrder() {
        val ns1 = nf.buildBasicNodes("====")
        val ns2 = nf.buildBasicNodes("---")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[-=]{3,4}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressDifferentAmountsOfPunctsBetweenAlphas() {
        val ns1 = nf.buildBasicNodes("ab.cd")
        val ns2 = nf.buildBasicNodes("ab..cd")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[ab]{2,2}[.]{1,2}[cd]{2,2}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressMissingPunct() {
        val ns1 = nf.buildBasicNodes("ab")
        val ns2 = nf.buildBasicNodes("ab.")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = dotStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressPunctWithAndWithoutAlphas() {
        val ns1 = nf.buildBasicNodes("ab.")
        val ns2 = nf.buildBasicNodes("ab.a")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = dotStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressAlphasAndAsterisk() {
        val ns1 = nf.buildBasicNodes("Specified")
        val ns2 = nf.buildBasicNodes("*")
        // Previously, this test case was resulting in an empty final regex.
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = dotStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressSquareBracketsAndOtherPunct() {
        val ns1 = nf.buildBasicNodes("#[")
        val ns2 = nf.buildBasicNodes("#]")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[#\\[\\]]{2,2}"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressSingleNumAndMultipleAlpha() {
        val ns1 = nf.buildBasicNodes("7")
        val ns2 = nf.buildBasicNodes("system")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = alnumLowerStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressWordsAndPunctuationMixed() {
        val ns1 = nf.buildBasicNodes("word1")
        val ns2 = nf.buildBasicNodes("word2")
        val ns3 = nf.buildBasicNodes("word3")
        val ns4 = nf.buildBasicNodes("---")
        val ns5 = nf.buildBasicNodes("==")
        var actual = compressor.compressNodes(ns1, ns2)
        actual = compressor.compressNodes(actual, ns3)
        actual = compressor.compressNodes(actual, ns4)
        actual = compressor.compressNodes(actual, ns5)
        var expected = dotStar
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressTokenIsRegex() {
        val ns1 = nf.buildBasicNodes("123.123.123.123")
        val ns2 = nf.parseNodes("$numStar[.]{1,1}$numStar[.]{1,1}$numStar[.]{1,1}$numStar")
        var actual = compressor.compressNodes(ns1, ns2)
        val expected = "$numStar[.]{1,1}$numStar[.]{1,1}$numStar[.]{1,1}$numStar"
        assertEquals(expected, nf.buildString(actual))
    }

    @Test
    fun compressCrazyPunctuationSpam() {
        val ns1 = nf.buildBasicNodes("12312-3-1=231-=")
        val ns2 = nf.buildBasicNodes("111-=-12=-312-23-=")
        var actual = compressor.compressNodes(ns1, ns2)
        var expected = "[123]{3,5}[-=]{1,3}[123]{1,2}[-=]{1,2}[123]{1,3}[-=]{1,1}[123]{2,3}[-=]{2,2}"
        assertEquals(expected, nf.buildString(actual))
    }
}
