package lushu.Lexer.Regex

fun testNodeInterval1To16() = Interval(1, 16)
fun testNodeInterval1To17() = Interval(1, 17)
fun testNodeInterval1To32() = Interval(1, 32)
fun testNodeInterval1To33() = Interval(1, 33)
fun testNodeInterval0To100() = Interval(0, 100)

fun testNodeAlphasUpper() = Node(stringsToChars(allAlphasUpper).toSet(), testNodeInterval1To32())
fun testNodeAlphasLower() = Node(stringsToChars(allAlphasLower).toSet(), testNodeInterval1To32())
fun testNodeNums() = Node(stringsToChars(allNums).toSet(), testNodeInterval1To16())
fun testNodePuncts() = Node(stringsToChars(allPuncts).toSet(), testNodeInterval1To16())

fun testBaseNodes(): List<Node> = listOf(
    testNodeAlphasUpper(),
    testNodeAlphasLower(),
    testNodeNums(),
    testNodePuncts()
)

fun testDisjointNodes(): List<Pair<Node, Node>> = listOf(
    Pair(testNodePuncts(), testNodeAlphasUpper()),
    Pair(testNodePuncts(), testNodeAlphasLower()),
    Pair(testNodePuncts(), testNodeNums())
)

val alphaLowerStar = "[abcdefghijklmnopqrstuvwxyz]*"
val numStar = "[0123456789]*"
val alnumLowerStar = "[0123456789abcdefghijklmnopqrstuvwxyz]*"
val punctStar = "[\"!#\$%&'()*+,-./:;<>=?@\\[\\]^_`{}|~\\\\]*"

val allAlphasLower: List<String> = listOf(
    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
    "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
    "u", "v", "w", "x", "y", "z"
)
val allAlphasUpper: List<String> = listOf(
    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
    "U", "V", "W", "X", "Y", "Z"
)
val allAlphas: List<String> = allAlphasLower + allAlphasUpper
val allNums: List<String> = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
val allAlnums: List<String> = allAlphas + allNums
val allPuncts: List<String> = listOf(
    "!", "\"", "#", """$""", "%", "&", "'", "(", ")",
    "*", "+", ",", "-", ".", "/", ":", ";", "<", "=",
    ">", "?", "@", "[", "\\", "]", "^", "_", "`",
    "{", "|", "}", "~"
)
val allChars = allAlnums + allPuncts
