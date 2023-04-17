package lushu.Grammar

import kotlin.text.Regex

class Token(
    var regex: String,
    var sensitive: Boolean = false
) {
    fun match(s: String): Boolean {
        return Regex(regex).matches(s)
    }
}
