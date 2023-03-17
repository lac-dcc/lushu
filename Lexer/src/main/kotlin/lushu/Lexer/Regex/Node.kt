package lushu.Lexer.Regex

val kleeneInterval = Interval(0, 0)

class Node(
    charset: Set<Char>,
    interval: Interval
) {
    var isTop: Boolean = false

    private var charsets: MutableList<Set<Char>> = mutableListOf<Set<Char>>(charset)
    private var interval: Interval = interval

    fun getCharset() = charsets.reduce { acc, charset -> acc + charset }
    fun addCharset(charset: Set<Char>) {
        charsets += charset
    }
    fun getInterval() = interval

    fun incrementInterval() {
        interval = Interval(interval.first + 1, interval.second + 1)
    }

    fun capInterval() {
        val max = if (interval.first > interval.second) interval.first else interval.second
        interval = Interval(max, max)
    }

    fun isKleene(): Boolean {
        return this.interval == kleeneInterval
    }

    fun kleenize() {
        this.interval = kleeneInterval
    }

    fun joinCharset(other: Node): Set<Char> {
        return this.getCharset().union(other.getCharset())
    }

    fun joinInterval(other: Node): Interval {
        var minLeft = this.getInterval().first
        if (other.getInterval().first < minLeft) {
            minLeft = other.getInterval().first
        }
        var maxRight = this.getInterval().second
        if (other.getInterval().second > maxRight) {
            maxRight = other.getInterval().second
        }
        return Interval(minLeft, maxRight)
    }

    override fun toString(): String {
        return "Node(charset=${getCharset()} interval=${getInterval()})"
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is Node -> this.getCharset() == other.getCharset() && this.interval == other.interval
        else -> false
    }
}

fun dummyNode(): Node {
    return Node(setOf(), Interval(1, 1))
}

fun topNode(): Node {
    return dummyNode().apply { isTop = true }
}
