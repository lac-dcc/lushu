package lushu.Merger.TestUtils

import lushu.Merger.Lattice.Node.Interval

class Fixtures {
    companion object {
        fun testNodeInterval1To16() = Interval(1, 16)
        fun testNodeInterval1To17() = Interval(1, 17)
        fun testNodeInterval1To32() = Interval(1, 32)
        fun testNodeInterval1To33() = Interval(1, 33)
        fun testNodeInterval0To100() = Interval(0, 100)

        val alphaLowerStar = "[abcdefghijklmnopqrstuvwxyz]+"
        val alphaStar = "[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+"
        val numStar = "[0123456789]+"
        val alnumLowerStar = "[0123456789abcdefghijklmnopqrstuvwxyz]+"
        val alnumStar = "[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+"
        val punctStar = "[\"!#\$%&'()*+,-./:;<>=?@\\[\\]^_`{}|~\\\\]+"

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
    }
}
