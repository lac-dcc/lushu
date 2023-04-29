package lushu.Grammar

import lushu.Merger.TestUtils.Utils
import org.junit.jupiter.api.Test

class NonTerminalTest {
    init {
        MergerS.load(Utils.basicConfigFullPath())
    }

    @Test
    fun testConsumeBasic() {
        data class TestCase(
            val desc: String,
            val input: List<String>,
            val expected: String
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "one word one letter",
                listOf<String>("a"),
                "[a]{1,1}"
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val rule = NonTerminal.new(it.input[0])
            rule.consume(it.input)
            val actual = rule.print()
            if (it.expected != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }
}
