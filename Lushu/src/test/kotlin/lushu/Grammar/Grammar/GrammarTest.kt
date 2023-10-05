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
            val line: String,
            val expected: Grammar.Result
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "one letter",
                "a",
                Grammar.Result(
                    listOf(
                        NonTerminal.Result(
                            listOf(
                                Terminal.Result(true, false, "a")
                            )
                        )
                    )
                )
            ),
            TestCase(
                "one word",
                "Hello",
                Grammar.Result(
                    listOf(
                        NonTerminal.Result(
                            listOf(
                                Terminal.Result(true, false, "Hello")
                            )
                        )
                    )
                )
            ),
            TestCase(
                "multiple words",
                "Hello World",
                Grammar.Result(
                    listOf(
                        NonTerminal.Result(
                            listOf(
                                Terminal.Result(true, false, "Hello"),
                                Terminal.Result(true, false, "World")
                            )
                        )
                    )
                )
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val grammar = Grammar.fromLine(it.line)
            val actual = grammar.consume(it.line)
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
            val line: String,
            val expected: Grammar.Result
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "phrase without IP",
                "Hello my name is bla bla bla",
                Grammar.Result(
                    listOf(
                        NonTerminal.Result(
                            listOf(
                                Terminal.Result(false, false, "Hello"),
                                Terminal.Result(false, false, "my"),
                                Terminal.Result(true, false, "name"),
                                Terminal.Result(true, false, "is"),
                                Terminal.Result(true, false, "bla"),
                                Terminal.Result(false, false, "bla"),
                                Terminal.Result(true, false, "bla")
                            )
                        )
                    )
                )
            ),
            TestCase(
                "phrase with IP in unknown position",
                "2023-04-29 00:00:00,123 Received request from extraWord 0.0.0.0",
                Grammar.Result(
                    listOf(
                        NonTerminal.Result(
                            listOf(
                                Terminal.Result(true, false, "2023-04-29"),
                                Terminal.Result(true, false, "00:00:00,123"),
                                Terminal.Result(true, false, "Received"),
                                Terminal.Result(true, false, "request"),
                                Terminal.Result(true, false, "from"),
                                Terminal.Result(false, false, "extraWord"),
                                Terminal.Result(true, false, "0.0.0.0")
                            )
                        )
                    )
                )
            ),
            TestCase(
                "phrase with IP in known position Case1",
                "2023-04-29 00:00:00,123 Received request from 0.0.0.0 extraWord",
                Grammar.Result(
                    listOf(
                        NonTerminal.Result(
                            listOf(
                                Terminal.Result(true, false, "2023-04-29"),
                                Terminal.Result(true, false, "00:00:00,123"),
                                Terminal.Result(true, false, "Received"),
                                Terminal.Result(true, false, "request"),
                                Terminal.Result(true, false, "from"),
                                Terminal.Result(true, true, "0.0.0.0"),
                                Terminal.Result(true, false, "extraWord")
                            )
                        )
                    )
                )
            ),
            TestCase(
                "phrase with IP in known position Case2",
                "2023-04-29 00:00:00,123 Received request from 1.2.3.4 extraWord",
                Grammar.Result(
                    listOf(
                        NonTerminal.Result(
                            listOf(
                                Terminal.Result(true, false, "2023-04-29"),
                                Terminal.Result(true, false, "00:00:00,123"),
                                Terminal.Result(true, false, "Received"),
                                Terminal.Result(true, false, "request"),
                                Terminal.Result(true, false, "from"),
                                Terminal.Result(true, true, "1.2.3.4"),
                                Terminal.Result(true, false, "extraWord")
                            )
                        )
                    )
                )
            ),
            TestCase(
                "phrase with IP in known position false positive",
                "2023-04-29 00:00:00,123 Received request from 123123.123123.123123.123123 extraWord",
                Grammar.Result(
                    listOf(
                        NonTerminal.Result(
                            listOf(
                                Terminal.Result(true, false, "2023-04-29"),
                                Terminal.Result(true, false, "00:00:00,123"),
                                Terminal.Result(true, false, "Received"),
                                Terminal.Result(true, false, "request"),
                                Terminal.Result(true, false, "from"),
                                Terminal.Result(true, true, "123123.123123.123123.123123"),
                                Terminal.Result(true, false, "extraWord")
                            )
                        )
                    )
                )
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val grammar = Grammar.fromTrainFile(
                Utils.logFullPath("train/ip-is-sensitive.txt")
            )
            val actual = grammar.consume(it.line)
            if (it.expected != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }
}
