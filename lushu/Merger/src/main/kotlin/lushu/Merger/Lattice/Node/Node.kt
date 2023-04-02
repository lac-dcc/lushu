package lushu.Merger.Lattice.Node

sealed class Node(
    val charset: Charset,
    val interval: Interval
) {
    fun joinCharset(other: Node): Charset {
        return charset.union(other.charset)
    }

    fun joinInterval(other: Node): Interval {
        var minLeft = this.interval.first
        if (other.interval.first < minLeft) {
            minLeft = other.interval.first
        }
        var maxRight = this.interval.second
        if (other.interval.second > maxRight) {
            maxRight = other.interval.second
        }
        return Interval(minLeft, maxRight)
    }

    override fun toString(): String {
        return "Node(charset=$charset interval=$interval)"
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is Node -> this.charset == other.charset && this.interval == other.interval
        else -> false
    }
}

class IntervalNode(
    val baseNode: PwsetNode,
    charset: Charset,
    interval: Interval
) : Node(charset, interval) {
    // isWithinBounds returns true if the node's interval is contained in its
    // respective base node interval.
    fun isWithinBounds(): Boolean {
        return interval.isWithinBound(baseNode.interval)
    }
}

// PwsetNode is a node with an identifier. The identifier is necessary because we
// must create base nodes from the start of the program.
class PwsetNode(
    val id: Int,
    private var blacklist: Set<Int>,
    charset: Charset,
    interval: Interval
) : Node(charset, interval) {
    fun addToBlacklist(nodeID: Int) {
        blacklist += nodeID
    }

    fun blacklist(): Set<Int> = blacklist

    fun blacklists(other: PwsetNode): Boolean {
        return blacklist.contains(other.id)
    }

    fun joinBlacklist(other: PwsetNode): Set<Int> {
        return blacklist.union(other.blacklist)
    }
}
