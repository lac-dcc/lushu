package lushu.Merger.Merger

import lushu.Merger.Config.Config
import lushu.Merger.Lattice.Node.Node
import lushu.Merger.Lattice.NodePrinter
import lushu.Merger.TestUtils.Fixtures
import lushu.Merger.TestUtils.Utils
import org.junit.jupiter.api.Test

// TODO: add tests to test Inference Machine properties (iterative, set-driven,
// etc) -aholmquist 2022-10-22
class MergerTest {
    private val cfg = Config.fromConfigFile(Utils.basicConfigFullPath())
    private val nf = cfg.nodeFactory
    private val merger = Merger(nf)

    @Test
    fun mergeOnce() {
        data class TestCase(
            val desc: String,
            val s1: String,
            val s2: String,
            val expected: String
        )
        val testCases = listOf<TestCase>(
            TestCase("empty", "", "", ""),
            TestCase("single alpha", "a", "a", "[a]{1,1}"),
            TestCase("single num", "1", "1", "[1]{1,1}"),
            TestCase("many alpha", "abcd", "abcd", "[abcd]{4,4}"),
            TestCase("many num", "4321", "1234", "[1234]{4,4}"),
            TestCase("alpha diff", "a", "b", "[ab]{1,1}"),
            TestCase("alpha num", "a", "1", Fixtures.alnumStar),
            TestCase("alpha mul repeated", "aaaa", "aaaa", "[a]{4,4}"),
            TestCase("alpha mul diff equal", "abcd", "abcd", "[abcd]{4,4}"),
            TestCase("alnum mul equal", "abc12", "abc12", Fixtures.alnumStar),
            TestCase("alpha reverse", "cba", "abc", "[abc]{3,3}"),
            TestCase("alpha mul diff diff", "abc", "efg", "[abcefg]{3,3}"),
            TestCase("alpha diff diffsize", "abc", "abcdef", "[abcdef]{3,6}"),
            TestCase("alpha diff diffsize reverse", "abcdef", "abc", "[abcdef]{3,6}"),
            TestCase("alnum mul samesize", "abc12", "efg34", Fixtures.alnumStar),
            TestCase(
                "timestamp",
                "00:00:00",
                "12:34:56",
                "[012]{2,2}[:]{1,1}[034]{2,2}[:]{1,1}[056]{2,2}"
            ),
            TestCase(
                "ip",
                "0.0.0.0",
                "12.34.56.78",
                "[012]{1,2}[.]{1,1}[034]{1,2}[.]{1,1}[056]{1,2}[.]{1,1}[078]{1,2}"
            ),
            TestCase(
                "sub ip",
                "1.2.3",
                "123.456.789",
                "[123]{1,3}[.]{1,1}[2456]{1,3}[.]{1,1}[3789]{1,3}"
            ),
            TestCase(
                "sub ip 2",
                "123.456.789",
                "12.34.78",
                "[123]{2,3}[.]{1,1}[3456]{2,3}[.]{1,1}[789]{2,3}"
            ),
            TestCase("alpha punct", "123a", "123:", NodePrinter.topRepr),
            TestCase("timestamp and number", "00:00:00", "123", NodePrinter.topRepr),
            TestCase("timestamp and partial timestamp", "00:00:00", "00:00", NodePrinter.topRepr),
            TestCase("missing punct", "ab", "ab.", NodePrinter.topRepr),
            TestCase("alpha and asterisk", "Specified", "*", NodePrinter.topRepr),
            TestCase("square brackets", "[", "]", "[\\[\\]]{1,1}"),
            TestCase("num and multiple alpha", "7", "system", Fixtures.alnumStar),
            TestCase(
                "crazy spam",
                "12312-3-1=231-=",
                "111--12=-312-23-=",
                "[123]{3,5}[-]{1,2}[123]{1,2}[-=]{1,2}[123]{1,3}[-=]{1,1}[123]{2,3}[-=]{2,2}"
            )
        )
        testCases.forEach {
            println("Starting test '${it.desc}'")
            val ns1 = nf.buildIntervalNodes(it.s1)
            val ns2 = nf.buildIntervalNodes(it.s2)
            val actual = merger.merge(ns1, ns2)
            val actualStr = NodePrinter.print(actual.tokens)
            if (it.expected != actualStr) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actualStr"
                )
            }
        }
    }

    @Test
    fun mergeMultipleTimes() {
        data class TestCase(
            val desc: String,
            // ss must have len >= 2
            val ss: List<String>,
            val expected: String
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "words and punct mixed",
                listOf<String>(
                    "word1",
                    "word2",
                    "word3",
                    "--",
                    "="
                ),
                NodePrinter.topRepr
            ),
            TestCase(
                "diff sizes twice",
                listOf<String>(
                    "abc",
                    "abcdef",
                    "abcdefghi"
                ),
                "[abcdefghi]{3,9}"
            ),
            TestCase(
                "timestamp twice",
                listOf<String>(
                    "00:00:00",
                    "12:34:56",
                    "12:34:56"
                ),
                "[012]{2,2}[:]{1,1}[034]{2,2}[:]{1,1}[056]{2,2}"
            )
        )
        testCases.forEach {
            println("Starting test '${it.desc}'")
            var actual: List<Node> = nf.buildIntervalNodes(it.ss[0])
            for (i in 1..it.ss.size - 1) {
                val ns = nf.buildIntervalNodes(it.ss[i])
                println("Merging $actual and ${it.ss[i]}")
                actual = merger.merge(actual, ns).tokens
            }
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
