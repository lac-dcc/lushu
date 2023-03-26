package lushu.Lexer.Merger

import lushu.Lexer.Config.Config
import lushu.Lexer.Lattice.LexerLattice
import lushu.Lexer.Lattice.Node.Node
import lushu.Lexer.TestUtils.TestNodeBuilder
import lushu.Lexer.TestUtils.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReducerTest {
    private val cfg = Config.fromConfigFile(Utils.basicConfigFullPath())
    private val nf = cfg.nodeFactory
    private val lattice = LexerLattice(nf)
    private val reducer = Reducer(lattice)

    @Test
    fun reduceEmpty() {
        val tokens = listOf<Node>()
        val actual = reducer.reduce(tokens)
        val expected = tokens
        assertEquals(expected, actual)
    }

    @Test
    fun reduceOneNode() {
        val tokens = nf.buildIntervalNodes("a")
        val actual = reducer.reduce(tokens)
        val expected = tokens
        assertEquals(expected, actual)
    }

    @Test
    fun reduceTwoNodesIntoOne() {
        val tokens = nf.buildIntervalNodes("ab")
        println(tokens)
        val actual = reducer.reduce(tokens)
        val expected = listOf<Node>(
            TestNodeBuilder.alphaIntervalNode(setOf<Char>('a', 'b'), 2, 2)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun reduceThreeNodesIntoOneNums() {
        val tokens = nf.buildIntervalNodes("123")
        val actual = reducer.reduce(tokens)
        val expected = listOf<Node>(
            TestNodeBuilder.alphaIntervalNode(setOf<Char>('1', '2', '3'), 3, 3)
        )
        assertEquals(expected, actual)
    }

    // @Test
    // fun reduceTop() {
    //     val tokens = nf.buildIntervalNodes("a:b")
    //     val actual = reducer.reduce(tokens)
    //     val expected = listOf<Node>(
    //         Node(setOf<Char>('a'), Interval(1, 1)),
    //         Node(setOf<Char>(':'), Interval(1, 1)),
    //         Node(setOf<Char>('b'), Interval(1, 1))
    //     )
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun reduceIP() {
    //     val tokens = nf.buildIntervalNodes("12.23.34.45")
    //     val actual = reducer.reduce(tokens)
    //     val expected = listOf<Node>(
    //         Node(setOf<Char>('1', '2'), Interval(2, 2)),
    //         Node(setOf<Char>('.'), Interval(1, 1)),
    //         Node(setOf<Char>('2', '3'), Interval(2, 2)),
    //         Node(setOf<Char>('.'), Interval(1, 1)),
    //         Node(setOf<Char>('3', '4'), Interval(2, 2)),
    //         Node(setOf<Char>('.'), Interval(1, 1)),
    //         Node(setOf<Char>('4', '5'), Interval(2, 2))
    //     )
    //     assertEquals(expected, actual)
    // }
}
