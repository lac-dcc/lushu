package lushu.Grammar.Grammar

import lushu.Merger.TestUtils.Utils
import org.junit.jupiter.api.Test

class GrammarTest {
    init {
        MergerS.load(Utils.basicConfigFullPath())
    }

    @Test
    fun testConsumeBasic() {
        data class TestCase(
            val desc: String,
            val input: String,
            val expected: String
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "one letter",
                "a",
                "a"
            ),
            TestCase(
                "one word",
                "Hello",
                "Hello"
            ),
            TestCase(
                "multiple words",
                "Hello World",
                "Hello World"
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val grammar = Grammar()
            val actual = grammar.consume(it.input)
            if (it.expected != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }

    @Test
    fun testConsumeObfuscate() {
        data class TestCase(
            val desc: String,
            val input: String,
            val expected: String
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "one letter",
                "a",
                "a"
            ),
            TestCase(
                "one word",
                "Hello",
                "Hello"
            ),
            TestCase(
                "multiple words",
                "Hello World",
                "Hello World"
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val grammar = Grammar()
            // Note that we train the grammar before running the test case
            //
            // TODO: grammar.train()
            val actual = grammar.consume(it.input)
            if (it.expected != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }
}
