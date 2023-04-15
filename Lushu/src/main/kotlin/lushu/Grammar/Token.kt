package lushu.Grammar.Grammar

import java.util.regex.Pattern

/**
 *
 * @author vitor
 */
class Token(
    var regex: String,
    var sensitive: Boolean = false
) {
    fun match(s: String): Boolean {
        return Pattern.compile(regex).matcher(s).matches()
    }
}
