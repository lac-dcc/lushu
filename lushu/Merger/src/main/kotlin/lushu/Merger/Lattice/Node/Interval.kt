package lushu.Merger.Lattice.Node

class Interval(val first: Int, val second: Int) {
    companion object {
        val kleene = Interval(0, 0)
    }

    fun cap(): Interval {
        val max = if (first > second) first else second
        return Interval(max, max)
    }

    fun increment(): Interval {
        return Interval(first + 1, second + 1)
    }

    fun isWithinBound(bound: Interval): Boolean {
        if (this.first < bound.first ||
            this.second > bound.second
        ) {
            return false
        }
        return true
    }

    override fun toString(): String {
        return "Interval($first, $second)"
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is Interval -> this.first == other.first && this.second == other.second
        else -> false
    }
}
