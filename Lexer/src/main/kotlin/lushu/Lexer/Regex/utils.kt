package lushu.Lexer.Regex

val dotStar = ".*"

// TODO: move charset stuff inside of a single class Charset
fun collapseCharset(charset: Set<Char>): String {
    var s = charset.toList().sorted().joinToString(separator = "")
    val mustEscape = "[]"
    mustEscape.forEach { c ->
        s = s.replace(c.toString(), "\\$c")
    }
    return s
}

fun stringsToChars(ss: List<String>): List<Char> {
    return ss.map { it[0] }
}

fun charsetFromS(s: String): Set<Char> {
    var cs = mutableSetOf<Char>()
    s.forEach {
        cs.add(it)
    }
    return cs
}
