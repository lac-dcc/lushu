package lushu.Lexer.Lattice

class FormatterTest {
    // private val nf = NodeFactory()
    // private val lattice = Lattice(
    //     testBaseNodes(),
    // )
    // private val formatter = Formatter(lattice)

    // @Test
    // fun formatNodesEmpty() {
    //     val tokens = listOf<Node>()
    //     val actual = formatter.formatNodes(tokens)
    //     val expected = tokens
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun formatNodesOneNode() {
    //     val tokens = nf.buildBasicNodes("a")
    //     val actual = formatter.formatNodes(tokens)
    //     val expected = tokens
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun formatNodesTwoNodesIntoOne() {
    //     val tokens = nf.buildBasicNodes("ab")
    //     val actual = formatter.formatNodes(tokens)
    //     val expected = listOf<Node>(
    //         Node(
    //             setOf<Char>('a', 'b'),
    //             Interval(2, 2)
    //         )
    //     )
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun formatNodesThreeNodesIntoOneNums() {
    //     val tokens = nf.buildBasicNodes("123")
    //     val actual = formatter.formatNodes(tokens)
    //     val expected = listOf<Node>(
    //         Node(
    //             setOf<Char>('1', '2', '3'),
    //             Interval(3, 3)
    //         )
    //     )
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun formatNodesTop() {
    //     val tokens = nf.buildBasicNodes("a:b")
    //     val actual = formatter.formatNodes(tokens)
    //     val expected = listOf<Node>(
    //         Node(setOf<Char>('a'), Interval(1, 1)),
    //         Node(setOf<Char>(':'), Interval(1, 1)),
    //         Node(setOf<Char>('b'), Interval(1, 1))
    //     )
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun formatNodesIP() {
    //     val tokens = nf.buildBasicNodes("12.23.34.45")
    //     val actual = formatter.formatNodes(tokens)
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
