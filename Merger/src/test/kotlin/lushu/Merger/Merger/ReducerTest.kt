package lushu.Merger.Merger

import lushu.Merger.Config.Config
import lushu.Merger.Lattice.MergerLattice
import lushu.Merger.Lattice.Node.Node
import lushu.Merger.Lattice.NodePrinter
import lushu.Merger.TestUtils.TestNodeBuilder
import lushu.Merger.TestUtils.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReducerTest {
    private val cfg = Config.fromConfigFile(Utils.basicConfigFullPath())
    private val nf = cfg.nodeFactory
    private val lattice = MergerLattice(nf)
    private val reducer = Reducer(lattice)

    @Test
    fun reduceBasic() {
        data class TestCase(
            val desc: String,
            val s: String,
            val expected: String
        )
        val testCases = listOf<TestCase>(
            TestCase("empty", "", ""),
            TestCase("alpha", "a", "[a]{1,1}"),
            TestCase("alpha multiple", "ab", "[ab]{2,2}"),
            TestCase("nums", "123", "[123]{3,3}"),
            TestCase("alpha with punct", "a:b", "[a]{1,1}[:]{1,1}[b]{1,1}"),
            TestCase("ip", "12.23.34.45",
                     "[12]{2,2}[.]{1,1}[23]{2,2}[.]{1,1}[34]{2,2}[.]{1,1}[45]{2,2}"),
        )
        testCases.forEach {
            println("Starting test '${it.desc}'")
            val ns = nf.buildIntervalNodes(it.s)
            val actual = reducer.reduce(ns)
            val actualStr = NodePrinter.print(actual)
            if (it.expected != actualStr) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actualStr"
                )
            }
        }
    }
}
