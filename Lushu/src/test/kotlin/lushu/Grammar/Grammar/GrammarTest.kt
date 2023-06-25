package lushu.Grammar.Grammar

import lushu.Test.Utils.Utils
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
            val grammar = Grammar.fromLine(it.input)
            val actual = grammar.consume(it.input)
            if (it.expected != actual.toString()) {
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
                "phrase without IP",
                "Hello my name is bla bla bla",
                "Hello my name is bla bla bla"
            ),
            TestCase(
                "phrase with IP in unknown position",
                "2023-04-29 00:00:00,123 Received request from extraWord 0.0.0.0",
                "2023-04-29 00:00:00,123 Received request from extraWord 0.0.0.0"
            ),
            TestCase(
                "phrase with IP in known position Case1",
                "2023-04-29 00:00:00,123 Received request from 0.0.0.0 extraWord",
                "2023-04-29 00:00:00,123 Received request from ${Terminal.constantMask} extraWord"
            ),
            TestCase(
                "phrase with IP in known position Case2",
                "2023-04-29 00:00:00,123 Received request from 1.2.3.4 extraWord",
                "2023-04-29 00:00:00,123 Received request from ${Terminal.constantMask} extraWord"
            ),
            TestCase(
                "phrase with IP in known position false positive",
                "2023-04-29 00:00:00,123 Received request from 123123.123123.123123.123123 extraWord",
                "2023-04-29 00:00:00,123 Received request from ${Terminal.constantMask} extraWord"
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val grammar = Grammar.fromTrainFile(
                Utils.logFullPath("train/ip-is-sensitive.log")
            )
            val actual = grammar.consume(it.input)
            if (it.expected != actual.toString()) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }
}
