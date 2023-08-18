package lushu.Merger.Lattice.Node

class Charset(
    val charset: Set<Char>
) {
    fun collapse(): String {
        var s = charset.toList().sorted().joinToString(separator = "")
        val mustEscape = "[]"
        mustEscape.forEach { c ->
            s = s.replace(c.toString(), "\\$c")
        }
        return s
    }

    fun union(other: Charset): Charset = Charset(charset.union(other.charset))

    companion object {
        fun stringsToChars(ss: List<String>): List<Char> = ss.map { it[0] }

        fun fromString(s: String): Charset {
            var cs = mutableSetOf<Char>()
            s.forEach {
                cs.add(it)
            }
            return Charset(cs)
        }
    }

    override fun hashCode(): Int = "$charset".hashCode()

    override fun toString(): String = "Charset($charset)"

    override fun equals(other: Any?) = when (other) {
        is Charset -> this.charset == other.charset
        else -> false
    }
}
