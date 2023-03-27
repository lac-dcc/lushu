package lushu.Merger.Lattice

import lushu.Merger.Config.Config
import lushu.Merger.Lattice.Node.Node
import lushu.Merger.TestUtils.TestNodeBuilder
import lushu.Merger.TestUtils.Utils
import org.junit.jupiter.api.Test

class MergerLatticeTest {
    private val cfg = Config.fromConfigFile(Utils.basicConfigFullPath())
    private val nf = cfg.nodeFactory
    private val lattice = MergerLattice(nf)

    @Test
    fun meetBasicCases() {
        data class TestCase(
            val desc: String,
            val n1: Node,
            val n2: Node,
            val expected: Node
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "empty charset",
                TestNodeBuilder.emptyIntervalNode(),
                TestNodeBuilder.emptyIntervalNode(),
                TestNodeBuilder.emptyIntervalNode()
            ),
            TestCase(
                "nonempty",
                TestNodeBuilder.emptyIntervalNode(),
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1),
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1)
            ),
            TestCase(
                "nonempty reverse",
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1),
                TestNodeBuilder.emptyIntervalNode(),
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1)
            ),
            TestCase(
                "equal",
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1),
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1),
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1)
            ),
            TestCase(
                "non-equal",
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1),
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('b'), 1, 1),
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a', 'b'), 1, 1)
            ),
            TestCase(
                "non-compatible nodes",
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1),
                TestNodeBuilder.punctIntervalNode(setOf<Char>('.'), 1, 1),
                nf.topNode()
            ),
            TestCase(
                "non-compatible nodes reverse order",
                TestNodeBuilder.punctIntervalNode(setOf<Char>('.'), 1, 1),
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1),
                nf.topNode()
            ),
            TestCase(
                "pwset node with interval node",
                TestNodeBuilder.alphaBaseNode(),
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1),
                TestNodeBuilder.alphaBaseNode()
            ),
            TestCase(
                "pwset node with interval node reverse order",
                TestNodeBuilder.alphaIntervalNode(setOf<Char>('a'), 1, 1),
                TestNodeBuilder.alphaBaseNode(),
                TestNodeBuilder.alphaBaseNode()
            ),
            TestCase(
                "pwset nodes not disjoint",
                TestNodeBuilder.alphaBaseNode(),
                TestNodeBuilder.numBaseNode(),
                TestNodeBuilder.alnumPwsetNode()
            ),
            TestCase(
                "pwset nodes disjoint",
                TestNodeBuilder.alphaBaseNode(),
                TestNodeBuilder.punctBaseNode(),
                nf.topNode()
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val actual = lattice.meet(it.n1, it.n2)
            if (it.expected != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }
}
