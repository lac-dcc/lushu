package lushu.Grammar.Grammar

import lushu.Merger.Merger.Token
import org.junit.jupiter.api.Test
import lushu.Test.Utils.Utils as MergerTestUtils

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
            val input: String,
            val tokens: List<Token>,
            val expected: Terminal.Result
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "one word",
                "a",
                buildTokens("abc"),
                Terminal.Result(true, "a")
            ),
            TestCase(
                "multiple words",
                "a",
                buildTokens("abc"),
                Terminal.Result(true, "a")
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
