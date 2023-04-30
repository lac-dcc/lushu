package lushu.Test.Utils

import lushu.Merger.Lattice.Node.Charset
import lushu.Merger.Lattice.Node.Interval
import lushu.Merger.Lattice.Node.IntervalNode
import lushu.Merger.Lattice.Node.PwsetNode

class TestNodeBuilder {
    companion object {
        fun alphaBaseNode(sensitive: Boolean = false): PwsetNode {
            return PwsetNode(
                1,
                setOf<Int>(),
                Charset(Fixtures.allAlphas),
                Fixtures.testNodeInterval1To32(),
                sensitive
            )
        }

        fun numBaseNode(sensitive: Boolean = false): PwsetNode {
            return PwsetNode(
                2,
                setOf<Int>(),
                Charset(Fixtures.allNums),
                Fixtures.testNodeInterval1To32(),
                sensitive
            )
        }

        fun punctBaseNode(sensitive: Boolean = false): PwsetNode {
            return PwsetNode(
                3,
                setOf<Int>(0, 1),
                Charset(Fixtures.allPuncts),
                Fixtures.testNodeInterval1To32(),
                sensitive
            )
        }

        fun alnumPwsetNode(): PwsetNode {
            // Assume that node was the first one to be created by a powerset
            // node join.
            return PwsetNode(
                4,
                setOf<Int>(),
                Charset(Fixtures.allAlnums),
                Interval(0, 0)
            )
        }

        fun alphaIntervalNode(
            chars: Set<Char>,
            intervalMin: Int,
            intervalMax: Int,
            sensitive: Boolean = false
        ): IntervalNode {
            return IntervalNode(
                alphaBaseNode(),
                Charset(chars),
                Interval(intervalMin, intervalMax),
                sensitive
            )
        }

        fun punctIntervalNode(
            chars: Set<Char>,
            intervalMin: Int,
            intervalMax: Int,
            sensitive: Boolean = false
        ): IntervalNode {
            return IntervalNode(
                punctBaseNode(),
                Charset(chars),
                Interval(intervalMin, intervalMax),
                sensitive
            )
        }

        fun emptyIntervalNode(): IntervalNode {
            return alphaIntervalNode(setOf<Char>(), 1, 1)
        }
    }
}
