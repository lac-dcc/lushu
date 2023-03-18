package lushu.Lexer.Lattice

class NodeFactoryTest {
    // private val nf = NodeFactory()

    // @Test
    // fun parseNodesEmpty() {
    //     val s = ""
    //     val actual = nf.parseNodes(s)
    //     val expected = listOf<Node>()
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun parseNodesA() {
    //     val s = "[a]{1,1}"
    //     val actual = nf.parseNodes(s)
    //     val expected = listOf<Node>(Node(setOf<Char>('a'), Interval(1, 1)))
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun parseNodesAB() {
    //     val s = "[ab]{1,1}"
    //     val actual = nf.parseNodes(s)
    //     val expected = listOf<Node>(Node(setOf<Char>('a', 'b'), Interval(1, 1)))
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun parseNodesKleene() {
    //     val s = "[ab]*"
    //     val actual = nf.parseNodes(s)
    //     val expected = listOf<Node>(Node(setOf<Char>('a', 'b'), Interval(0, 0)))
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun parseNodesMultipleNodes() {
    //     val s = "[ab]{1,1}[:]{1,1}"
    //     val actual = nf.parseNodes(s)
    //     val expected = listOf<Node>(
    //         Node(setOf<Char>('a', 'b'), Interval(1, 1)),
    //         Node(setOf<Char>(':'), Interval(1, 1))
    //     )
    //     assertEquals(expected, actual)
    // }

    // @Test
    // fun parseNodesMultipleNodesWithKleene() {
    //     val s = "[ab]{1,1}[:]{1,1}[321]*"
    //     val actual = nf.parseNodes(s)
    //     val expected = listOf<Node>(
    //         Node(setOf<Char>('a', 'b'), Interval(1, 1)),
    //         Node(setOf<Char>(':'), Interval(1, 1)),
    //         Node(setOf<Char>('1', '2', '3'), Interval(0, 0))
    //     )
    //     assertEquals(expected, actual)
    // }
}
