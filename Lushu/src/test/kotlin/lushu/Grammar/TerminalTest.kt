package lushu.Grammar

import lushu.Merger.Merger.Token
import org.junit.jupiter.api.Test
import lushu.Merger.TestUtils.Utils as MergerTestUtils

class TerminalTest {
    init {
        MergerS.load(MergerTestUtils.basicConfigFullPath())
    }

    private fun buildTokens(s: String): List<Token> {
        return MergerS.merger().tokensFromString(s)
    }

    @Test
    fun testConsumeBasic() {
        data class TestCase(
            val desc: String,
            val input: List<String>,
            val tokens: List<Token>,
            val expected: List<String>
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "one word",
                listOf<String>("a"),
                buildTokens("abc"),
                listOf<String>()
            ),
            TestCase(
                "multiple words",
                listOf<String>("a", "b", "c"),
                buildTokens("abc"),
                listOf<String>("b", "c")
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val rule = Terminal(it.tokens)
            val actual = rule.consume(it.input)
            if (it.expected != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }
}
