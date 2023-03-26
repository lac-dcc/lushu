package lushu.Lexer.Merger

import lushu.Lexer.Config.Config
import lushu.Lexer.Lattice.LexerLattice
import lushu.Lexer.Lattice.Node.Node
import lushu.Lexer.TestUtils.TestNodeBuilder
import lushu.Lexer.TestUtils.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// TODO: add tests to test Inference Machine properties (iterative, set-driven,
// etc) -aholmquist 2022-10-22
class MergerTest {
    private val cfg = Config.fromConfigFile(Utils.basicConfigFullPath())
    private val nf = cfg.nodeFactory
    private val lattice = LexerLattice(nf)
    private val merger = Merger(lattice)

    @Test
    fun mergeEmpty() {
        val ns1 = nf.buildIntervalNodes("")
        val ns2 = nf.buildIntervalNodes("")
        val actual = merger.merge(ns1, ns2)
        val expected = listOf<Node>()
        assertEquals(expected, actual)
    }

    @Test
    fun mergeOneAlpha() {
        val ns1 = nf.buildIntervalNodes("")
        val ns2 = nf.buildIntervalNodes("a")
        val actual = merger.merge(ns1, ns2)
        val expected = listOf<Node>(
            TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1)
        )
        assertEquals(expected, actual)
    }

    // @Test
    // fun mergeOneNum() {
    //     val ns1 = nf.buildIntervalNodes("")
    //     val ns2 = nf.buildIntervalNodes("1")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[1]{1,1}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeManyChar() {
    //     val ns1 = nf.buildIntervalNodes("")
    //     val ns2 = nf.buildIntervalNodes("abcd")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcd]{4,4}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeManyNum() {
    //     val ns1 = nf.buildIntervalNodes("")
    //     val ns2 = nf.buildIntervalNodes("1234")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[1234]{4,4}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingSingletonEqual() {
    //     val ns1 = nf.buildIntervalNodes("a")
    //     val ns2 = nf.buildIntervalNodes("a")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[a]{1,1}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingSingletonDiff() {
    //     val ns1 = nf.buildIntervalNodes("a")
    //     val ns2 = nf.buildIntervalNodes("b")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[ab]{1,1}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingSingletonAlphaNum() {
    //     val ns1 = nf.buildIntervalNodes("a")
    //     val ns2 = nf.buildIntervalNodes("1")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = alnumLowerStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingMultipleRepeated() {
    //     val ns1 = nf.buildIntervalNodes("aaaa")
    //     val ns2 = nf.buildIntervalNodes("aaaa")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[a]{4,4}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingMultipleEqual() {
    //     val ns1 = nf.buildIntervalNodes("abcd")
    //     val ns2 = nf.buildIntervalNodes("abcd")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcd]{4,4}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingMultipleEqualAlphaAndNum() {
    //     val ns1 = nf.buildIntervalNodes("abc12")
    //     val ns2 = nf.buildIntervalNodes("abc12")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = alnumLowerStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingMultipleTwoDiffOneEqual() {
    //     val ns1 = nf.buildIntervalNodes("cba")
    //     val ns2 = nf.buildIntervalNodes("abc")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abc]{3,3}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingMultipleAllDiff() {
    //     val ns1 = nf.buildIntervalNodes("abc")
    //     val ns2 = nf.buildIntervalNodes("efg")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcefg]{3,3}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingMultipleDifferentSizes() {
    //     val ns1 = nf.buildIntervalNodes("abc")
    //     val ns2 = nf.buildIntervalNodes("abcdef")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcdef]{3,6}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingMultipleDifferentSizesTwice() {
    //     val ns1 = nf.buildIntervalNodes("abc")
    //     val ns2 = nf.buildIntervalNodes("abcdef")
    //     val actual1 = merger.merge(ns1, ns2)
    //     val expected1 = "[abcdef]{3,6}"
    //     assertEquals(expected1, actual1)

    //     val ns3 = nf.buildIntervalNodes("abcdefghi")
    //     val actual2 = merger.merge(actual1, ns3)
    //     val expected2 = "[abcdefghi]{3,9}"
    //     assertEquals(expected2, actual2)
    // }

    // @Test
    // fun mergeFromExistingPreviousIsLonger() {
    //     val ns1 = nf.buildIntervalNodes("abcdef")
    //     val ns2 = nf.buildIntervalNodes("abc")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcdef]{3,6}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeFromExistingMultipleAlphaNumDiff() {
    //     val ns1 = nf.buildIntervalNodes("abc12")
    //     val ns2 = nf.buildIntervalNodes("efg34")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = alnumLowerStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeTimestamp() {
    //     val ns1 = nf.buildIntervalNodes("00:00:00")
    //     val ns2 = nf.buildIntervalNodes("12:34:56")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[012]{2,2}[:]{1,1}[034]{2,2}[:]{1,1}[056]{2,2}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeTimestampTwice() {
    //     val ns1 = nf.buildIntervalNodes("00:00:00")
    //     val ns2 = nf.buildIntervalNodes("12:34:56")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[012]{2,2}[:]{1,1}[034]{2,2}[:]{1,1}[056]{2,2}"
    //     assertEquals(expected, actual)

    //     val ns3 = nf.buildIntervalNodes("12:34:56")
    //     actual = merger.merge(actual, ns3)
    //     // Regex should remain the same, given that we have already seen this
    //     // example in the past.
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeIP() {
    //     val ns1 = nf.buildIntervalNodes("0.0.0.0")
    //     val ns2 = nf.buildIntervalNodes("12.34.56.78")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[012]{1,2}[.]{1,1}[034]{1,2}[.]{1,1}[056]{1,2}[.]{1,1}[078]{1,2}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeSubIP() {
    //     val ns1 = nf.buildIntervalNodes("1.2.3")
    //     val ns2 = nf.buildIntervalNodes("123.456.789")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[123]{1,3}[.]{1,1}[2456]{1,3}[.]{1,1}[3789]{1,3}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeIPAndSubIPCommonNumbers() {
    //     val ns1 = nf.buildIntervalNodes("123.456.789")
    //     val ns2 = nf.buildIntervalNodes("12.34.78")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[123]{2,3}[.]{1,1}[3456]{2,3}[.]{1,1}[789]{2,3}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeIPAndSubIPCommonNumbersTwice() {
    //     val ns1 = nf.buildIntervalNodes("123.456.789")
    //     val ns2 = nf.buildIntervalNodes("12.34.78")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[123]{2,3}[.]{1,1}[3456]{2,3}[.]{1,1}[789]{2,3}"
    //     assertEquals(expected, actual)

    //     // Shouldn't change
    //     actual = merger.merge(nf.parseNodes(expected), ns2)
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeAlphaAndPunctSamePlace() {
    //     val ns1 = nf.buildIntervalNodes("123a")
    //     val ns2 = nf.buildIntervalNodes("123:")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeTimestampAndNumber() {
    //     val ns1 = nf.buildIntervalNodes("00:00:00")
    //     val ns2 = nf.buildIntervalNodes("123")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeTimestampAndPartialTimestamp() {
    //     val ns1 = nf.buildIntervalNodes("00:00:00")
    //     val ns2 = nf.buildIntervalNodes("00:00")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeTimestampAndPartialTimestampReverseOrder() {
    //     val ns1 = nf.buildIntervalNodes("00:00")
    //     val ns2 = nf.buildIntervalNodes("00:00:00")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergePunctsDifferentSizes() {
    //     val ns1 = nf.buildIntervalNodes("---")
    //     val ns2 = nf.buildIntervalNodes("====")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[-=]{3,4}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergePunctsDifferentSizesReverseOrder() {
    //     val ns1 = nf.buildIntervalNodes("====")
    //     val ns2 = nf.buildIntervalNodes("---")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[-=]{3,4}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeDifferentAmountsOfPunctsBetweenAlphas() {
    //     val ns1 = nf.buildIntervalNodes("ab.cd")
    //     val ns2 = nf.buildIntervalNodes("ab..cd")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[ab]{2,2}[.]{1,2}[cd]{2,2}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeMissingPunct() {
    //     val ns1 = nf.buildIntervalNodes("ab")
    //     val ns2 = nf.buildIntervalNodes("ab.")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergePunctWithAndWithoutAlphas() {
    //     val ns1 = nf.buildIntervalNodes("ab.")
    //     val ns2 = nf.buildIntervalNodes("ab.a")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeAlphasAndAsterisk() {
    //     val ns1 = nf.buildIntervalNodes("Specified")
    //     val ns2 = nf.buildIntervalNodes("*")
    //     // Previously, this test case was resulting in an empty final regex.
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeSquareBracketsAndOtherPunct() {
    //     val ns1 = nf.buildIntervalNodes("#[")
    //     val ns2 = nf.buildIntervalNodes("#]")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[#\\[\\]]{2,2}"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeSingleNumAndMultipleAlpha() {
    //     val ns1 = nf.buildIntervalNodes("7")
    //     val ns2 = nf.buildIntervalNodes("system")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = alnumLowerStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeWordsAndPunctuationMixed() {
    //     val ns1 = nf.buildIntervalNodes("word1")
    //     val ns2 = nf.buildIntervalNodes("word2")
    //     val ns3 = nf.buildIntervalNodes("word3")
    //     val ns4 = nf.buildIntervalNodes("---")
    //     val ns5 = nf.buildIntervalNodes("==")
    //     var actual = merger.merge(ns1, ns2)
    //     actual = merger.merge(actual, ns3)
    //     actual = merger.merge(actual, ns4)
    //     actual = merger.merge(actual, ns5)
    //     var expected = dotStar
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeTokenIsRegex() {
    //     val ns1 = nf.buildIntervalNodes("123.123.123.123")
    //     val ns2 = nf.parseNodes("$numStar[.]{1,1}$numStar[.]{1,1}$numStar[.]{1,1}$numStar")
    //     var actual = merger.merge(ns1, ns2)
    //     val expected = "$numStar[.]{1,1}$numStar[.]{1,1}$numStar[.]{1,1}$numStar"
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun mergeCrazyPunctuationSpam() {
    //     val ns1 = nf.buildIntervalNodes("12312-3-1=231-=")
    //     val ns2 = nf.buildIntervalNodes("111-=-12=-312-23-=")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[123]{3,5}[-=]{1,3}[123]{1,2}[-=]{1,2}[123]{1,3}[-=]{1,1}[123]{2,3}[-=]{2,2}"
    //     assertEquals(expected, actual)
    // }
}
