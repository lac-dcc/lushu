package lushu.Lexer.TestUtils

import lushu.Lexer.Lattice.Node.Charset
import lushu.Lexer.Lattice.Node.Interval
import lushu.Lexer.Lattice.Node.IntervalNode
import lushu.Lexer.Lattice.Node.PwsetNode

class TestNodeBuilder {
    companion object {
        fun alphaBaseNode(): PwsetNode {
            return PwsetNode(
                0,
                setOf<Int>(),
                Charset(allAlphas),
                testNodeInterval1To32()
            )
        }

        fun numBaseNode(): PwsetNode {
            return PwsetNode(
                1,
                setOf<Int>(),
                Charset(allNums),
                testNodeInterval1To32()
            )
        }

        fun punctBaseNode(): PwsetNode {
            return PwsetNode(
                2,
                setOf<Int>(0, 1),
                Charset(allPuncts),
                testNodeInterval1To32()
            )
        }

        fun alphaIntervalNode(
            chars: Set<Char>,
            intervalMin: Int,
            intervalMax: Int
        ): IntervalNode {
            return IntervalNode(
                alphaBaseNode(),
                Charset(chars),
                Interval(intervalMin, intervalMax)
            )
        }
    }
}
