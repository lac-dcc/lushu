package lushu.Grammar.Grammar

import org.junit.jupiter.api.Test
import lushu.Test.Utils.Utils as MergerTestUtils

class TerminalTest {
    init {
        MergerS.load(MergerTestUtils.basicConfigFullPath())
    }

    @Test
    fun testConsumeBasic() {
        data class TestCase(
            val desc: String,
            val tokens: String,
            val input: String,
            val expected: Terminal.Result
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "one token",
                "abc",
                "a",
                Terminal.Result(true, "a")
            ),
            TestCase(
                "multiple tokens",
                "abcdefghijklmnop",
                "HelloFriend",
                Terminal.Result(true, "HelloFriend")
            ),
            TestCase(
                "multiple words unmergeable",
                "abc",
                "...",
                Terminal.Result(false, "...")
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val rule = Terminal.new(it.tokens)
            val actual = rule.consume(it.input)
            if (it.expected.consumed != actual.consumed || it.expected.obfuscated != actual.obfuscated) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }

    @Test
    fun testCaptureSensitivity() {
        data class TestCase(
            val desc: String,
            val word: String,
            val expected: String
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "not sensitive",
                "a",
                ""
            ),
            TestCase(
                "sensitive",
                "<s>a</s>",
                "a"
            ),
            TestCase(
                "sensitive ip",
                "<s>0.0.0.0</s>",
                "0.0.0.0"
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val actual = Terminal.captureSensitivity(it.word)
            if (it.expected != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }
}
