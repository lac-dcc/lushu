package lushu.Lexer.Merger

// TODO: add tests to test Inference Machine properties (iterative, set-driven,
// etc) -aholmquist 2022-10-22
class MergerTest {
    // private val nf = NodeFactory()
    // private val lattice = LexerLattice(testBaseNodes())
    // private val merger = Merger(lattice)

    // @Test
    // fun meldEmpty() {
    //     val ns1 = nf.buildBasicNodes("")
    //     val ns2 = nf.buildBasicNodes("")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = ""
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldOneAlpha() {
    //     val ns1 = nf.buildBasicNodes("")
    //     val ns2 = nf.buildBasicNodes("a")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[a]{1,1}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldOneNum() {
    //     val ns1 = nf.buildBasicNodes("")
    //     val ns2 = nf.buildBasicNodes("1")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[1]{1,1}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldManyChar() {
    //     val ns1 = nf.buildBasicNodes("")
    //     val ns2 = nf.buildBasicNodes("abcd")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcd]{4,4}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldManyNum() {
    //     val ns1 = nf.buildBasicNodes("")
    //     val ns2 = nf.buildBasicNodes("1234")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[1234]{4,4}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingSingletonEqual() {
    //     val ns1 = nf.buildBasicNodes("a")
    //     val ns2 = nf.buildBasicNodes("a")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[a]{1,1}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingSingletonDiff() {
    //     val ns1 = nf.buildBasicNodes("a")
    //     val ns2 = nf.buildBasicNodes("b")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[ab]{1,1}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingSingletonAlphaNum() {
    //     val ns1 = nf.buildBasicNodes("a")
    //     val ns2 = nf.buildBasicNodes("1")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = alnumLowerStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingMultipleRepeated() {
    //     val ns1 = nf.buildBasicNodes("aaaa")
    //     val ns2 = nf.buildBasicNodes("aaaa")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[a]{4,4}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingMultipleEqual() {
    //     val ns1 = nf.buildBasicNodes("abcd")
    //     val ns2 = nf.buildBasicNodes("abcd")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcd]{4,4}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingMultipleEqualAlphaAndNum() {
    //     val ns1 = nf.buildBasicNodes("abc12")
    //     val ns2 = nf.buildBasicNodes("abc12")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = alnumLowerStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingMultipleTwoDiffOneEqual() {
    //     val ns1 = nf.buildBasicNodes("cba")
    //     val ns2 = nf.buildBasicNodes("abc")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abc]{3,3}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingMultipleAllDiff() {
    //     val ns1 = nf.buildBasicNodes("abc")
    //     val ns2 = nf.buildBasicNodes("efg")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcefg]{3,3}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingMultipleDifferentSizes() {
    //     val ns1 = nf.buildBasicNodes("abc")
    //     val ns2 = nf.buildBasicNodes("abcdef")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcdef]{3,6}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingMultipleDifferentSizesTwice() {
    //     val ns1 = nf.buildBasicNodes("abc")
    //     val ns2 = nf.buildBasicNodes("abcdef")
    //     val actual1 = merger.merge(ns1, ns2)
    //     val expected1 = "[abcdef]{3,6}"
    //     assertEquals(expected1, nf.buildString(actual1))

    //     val ns3 = nf.buildBasicNodes("abcdefghi")
    //     val actual2 = merger.merge(actual1, ns3)
    //     val expected2 = "[abcdefghi]{3,9}"
    //     assertEquals(expected2, nf.buildString(actual2))
    // }

    // @Test
    // fun meldFromExistingPreviousIsLonger() {
    //     val ns1 = nf.buildBasicNodes("abcdef")
    //     val ns2 = nf.buildBasicNodes("abc")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[abcdef]{3,6}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldFromExistingMultipleAlphaNumDiff() {
    //     val ns1 = nf.buildBasicNodes("abc12")
    //     val ns2 = nf.buildBasicNodes("efg34")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = alnumLowerStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldTimestamp() {
    //     val ns1 = nf.buildBasicNodes("00:00:00")
    //     val ns2 = nf.buildBasicNodes("12:34:56")
    //     val actual = merger.merge(ns1, ns2)
    //     val expected = "[012]{2,2}[:]{1,1}[034]{2,2}[:]{1,1}[056]{2,2}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldTimestampTwice() {
    //     val ns1 = nf.buildBasicNodes("00:00:00")
    //     val ns2 = nf.buildBasicNodes("12:34:56")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[012]{2,2}[:]{1,1}[034]{2,2}[:]{1,1}[056]{2,2}"
    //     assertEquals(expected, nf.buildString(actual))

    //     val ns3 = nf.buildBasicNodes("12:34:56")
    //     actual = merger.merge(actual, ns3)
    //     // Regex should remain the same, given that we have already seen this
    //     // example in the past.
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldIP() {
    //     val ns1 = nf.buildBasicNodes("0.0.0.0")
    //     val ns2 = nf.buildBasicNodes("12.34.56.78")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[012]{1,2}[.]{1,1}[034]{1,2}[.]{1,1}[056]{1,2}[.]{1,1}[078]{1,2}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldSubIP() {
    //     val ns1 = nf.buildBasicNodes("1.2.3")
    //     val ns2 = nf.buildBasicNodes("123.456.789")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[123]{1,3}[.]{1,1}[2456]{1,3}[.]{1,1}[3789]{1,3}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldIPAndSubIPCommonNumbers() {
    //     val ns1 = nf.buildBasicNodes("123.456.789")
    //     val ns2 = nf.buildBasicNodes("12.34.78")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[123]{2,3}[.]{1,1}[3456]{2,3}[.]{1,1}[789]{2,3}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldIPAndSubIPCommonNumbersTwice() {
    //     val ns1 = nf.buildBasicNodes("123.456.789")
    //     val ns2 = nf.buildBasicNodes("12.34.78")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[123]{2,3}[.]{1,1}[3456]{2,3}[.]{1,1}[789]{2,3}"
    //     assertEquals(expected, nf.buildString(actual))

    //     // Shouldn't change
    //     actual = merger.merge(nf.parseNodes(expected), ns2)
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldAlphaAndPunctSamePlace() {
    //     val ns1 = nf.buildBasicNodes("123a")
    //     val ns2 = nf.buildBasicNodes("123:")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldTimestampAndNumber() {
    //     val ns1 = nf.buildBasicNodes("00:00:00")
    //     val ns2 = nf.buildBasicNodes("123")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldTimestampAndPartialTimestamp() {
    //     val ns1 = nf.buildBasicNodes("00:00:00")
    //     val ns2 = nf.buildBasicNodes("00:00")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldTimestampAndPartialTimestampReverseOrder() {
    //     val ns1 = nf.buildBasicNodes("00:00")
    //     val ns2 = nf.buildBasicNodes("00:00:00")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldPunctsDifferentSizes() {
    //     val ns1 = nf.buildBasicNodes("---")
    //     val ns2 = nf.buildBasicNodes("====")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[-=]{3,4}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldPunctsDifferentSizesReverseOrder() {
    //     val ns1 = nf.buildBasicNodes("====")
    //     val ns2 = nf.buildBasicNodes("---")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[-=]{3,4}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldDifferentAmountsOfPunctsBetweenAlphas() {
    //     val ns1 = nf.buildBasicNodes("ab.cd")
    //     val ns2 = nf.buildBasicNodes("ab..cd")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[ab]{2,2}[.]{1,2}[cd]{2,2}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldMissingPunct() {
    //     val ns1 = nf.buildBasicNodes("ab")
    //     val ns2 = nf.buildBasicNodes("ab.")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldPunctWithAndWithoutAlphas() {
    //     val ns1 = nf.buildBasicNodes("ab.")
    //     val ns2 = nf.buildBasicNodes("ab.a")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldAlphasAndAsterisk() {
    //     val ns1 = nf.buildBasicNodes("Specified")
    //     val ns2 = nf.buildBasicNodes("*")
    //     // Previously, this test case was resulting in an empty final regex.
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = dotStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldSquareBracketsAndOtherPunct() {
    //     val ns1 = nf.buildBasicNodes("#[")
    //     val ns2 = nf.buildBasicNodes("#]")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[#\\[\\]]{2,2}"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldSingleNumAndMultipleAlpha() {
    //     val ns1 = nf.buildBasicNodes("7")
    //     val ns2 = nf.buildBasicNodes("system")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = alnumLowerStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldWordsAndPunctuationMixed() {
    //     val ns1 = nf.buildBasicNodes("word1")
    //     val ns2 = nf.buildBasicNodes("word2")
    //     val ns3 = nf.buildBasicNodes("word3")
    //     val ns4 = nf.buildBasicNodes("---")
    //     val ns5 = nf.buildBasicNodes("==")
    //     var actual = merger.merge(ns1, ns2)
    //     actual = merger.merge(actual, ns3)
    //     actual = merger.merge(actual, ns4)
    //     actual = merger.merge(actual, ns5)
    //     var expected = dotStar
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldTokenIsRegex() {
    //     val ns1 = nf.buildBasicNodes("123.123.123.123")
    //     val ns2 = nf.parseNodes("$numStar[.]{1,1}$numStar[.]{1,1}$numStar[.]{1,1}$numStar")
    //     var actual = merger.merge(ns1, ns2)
    //     val expected = "$numStar[.]{1,1}$numStar[.]{1,1}$numStar[.]{1,1}$numStar"
    //     assertEquals(expected, nf.buildString(actual))
    // }

    // @Test
    // fun meldCrazyPunctuationSpam() {
    //     val ns1 = nf.buildBasicNodes("12312-3-1=231-=")
    //     val ns2 = nf.buildBasicNodes("111-=-12=-312-23-=")
    //     var actual = merger.merge(ns1, ns2)
    //     var expected = "[123]{3,5}[-=]{1,3}[123]{1,2}[-=]{1,2}[123]{1,3}[-=]{1,1}[123]{2,3}[-=]{2,2}"
    //     assertEquals(expected, nf.buildString(actual))
    // }
}
