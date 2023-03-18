package lushu.Lexer.Lattice.Node

class Interval(val first: Int, val second: Int) {
    companion object {
        val kleene = Interval(0, 0)
    }

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
