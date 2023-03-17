package lushu.Lexer.Regex

class Interval(val first: Int, val second: Int) {
    fun isWithinBound(bound: Interval): Boolean {
        if (this.first < bound.first ||
            this.second > bound.second
        ) {
            return false
        }
        return true
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is Interval -> this.first == other.first && this.second == other.second
        else -> false
    }

    override fun toString(): String {
        return "Interval($first, $second)"
    }
}
