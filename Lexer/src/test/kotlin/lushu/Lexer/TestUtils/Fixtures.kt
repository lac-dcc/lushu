package lushu.Lexer.TestUtils

import lushu.Lexer.Lattice.Node.Interval

fun testNodeInterval1To16() = Interval(1, 16)
fun testNodeInterval1To17() = Interval(1, 17)
fun testNodeInterval1To32() = Interval(1, 32)
fun testNodeInterval1To33() = Interval(1, 33)
fun testNodeInterval0To100() = Interval(0, 100)

// fun testNodeAlphasUpper() = PwsetNode(Charset.stringsToChars(allAlphasUpper).toSet(), testNodeInterval1To32(), "aupper")
// fun testNodeAlphasLower() = PwsetNode(Charset.stringsToChars(allAlphasLower).toSet(), testNodeInterval1To32(), "alower")
// fun testNodeAlphas() = PwsetNode(Charset.stringsToChars(allAlphas).toSet(), testNodeInterval1To32(), "alpha")
// fun testNodeNums() = PwsetNode(Charset.stringsToChars(allNums).toSet(), testNodeInterval1To16(), "num")
// fun testNodePuncts() = PwsetNode(Charset.stringsToChars(allPuncts).toSet(), testNodeInterval1To16(), "punct", setOf("alpha", "aupper", "alower", "aupper", "num"))

// fun testBaseNodes(): Set<Node> = setOf(
//     testNodeAlphasUpper(),
//     testNodeAlphasLower(),
//     testNodeNums(),
//     testNodePuncts()
// )

val alphaLowerStar = "[abcdefghijklmnopqrstuvwxyz]*"
val numStar = "[0123456789]*"
val alnumLowerStar = "[0123456789abcdefghijklmnopqrstuvwxyz]*"
val punctStar = "[\"!#\$%&'()*+,-./:;<>=?@\\[\\]^_`{}|~\\\\]*"

val allAlphasLower: Set<Char> = setOf(
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
    'u', 'v', 'w', 'x', 'y', 'z'
)
val allAlphasUpper: Set<Char> = setOf(
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
    'U', 'V', 'W', 'X', 'Y', 'Z'
)
val allAlphas: Set<Char> = allAlphasLower + allAlphasUpper
val allNums: Set<Char> = setOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
val allAlnums: Set<Char> = allAlphas + allNums
val allPuncts: Set<Char> = setOf(
    '!', '\'', '#', '$', '%', '&', '"', '(', ')',
    '*', '+', ',', '-', '.', '/', ':', ';', '<', '=',
    '>', '?', '@', '[', '\\', ']', '^', '_', '`',
    '{', '|', '}', '~'
)
val allChars = allAlnums + allPuncts
