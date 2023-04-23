package lushu.Grammar.Node

import org.junit.jupiter.api.Test

class TerminalTest {

    private val terminal = Terminal()

    @Test
    fun testParse() {
        data class TestCase(val desc: String, val input: List<String>, val expected: String)
        val testCases = listOf<TestCase>(
            TestCase("Empty", listOf(), ""),
            TestCase("NonSensitiveMatch", listOf("The"), "The"),
            TestCase("NonSensitiveAddToken", listOf("Quick"), "Quick"),
            TestCase("NonSensitiveMatchAndAddToken", listOf("Quick"), "Quick"),
            TestCase("SensitiveMatch", listOf("<s>192.158.1.102</s>"), "*************"),
            TestCase("SensitiveAddToken", listOf("<s>192.158.1.103</s>"), "*************"),
            TestCase("SensitiveMatchAndAddToken", listOf("<s>192.158.1.103</s>"), "*************"),
            TestCase("SensitiveNonMatch", listOf("<s>192.158.1.104</s>"), "<s>192.158.1.104</s>"),
            TestCase("MixedMatch", listOf("The", "<s>192.158.1.105</s>"), "The *************"),
            TestCase("MixedAddToken", listOf("Quick", "<s>192.158.1.106</s>"), "Quick *************"),
            TestCase("MixedMatchAndAddToken", listOf("Quick", "<s>192.158.1.107</s>"), "Quick *************")
        )

        testCases.forEach {
            val parsed = terminal.parse(it.input)
            if (it.expected != parsed) {
                throw Exception(
                    "For test '${it.desc}', " +
                        "expected ${it.expected}, " +
                        "but got $parsed."
                )
            }
        }
    }

    @Test
    fun testhasFlagSensitive() {
        data class TestCase(val desc: String, val input: String, val expected: Pair<String, Boolean>)
        val testCases = listOf<TestCase>(
            TestCase("Empty", "", Pair("", false)),
            TestCase("NonSensitiveMatch", "Quick", Pair("Quick", false)),
            TestCase("String<", "<", Pair("<", false)),
            TestCase("StringStartedW<s>", "<s>anything", Pair("<s>anything", false)),
            TestCase("SensitiveMatchWord", "<s>anything</s>", Pair("anything", true)),
            TestCase("SensitiveMatchNumbers", "<s>192.158.1.106</s>", Pair("192.158.1.106", true))
        )

        testCases.forEach {
            val hadFlagSensitive = terminal.hasFlagSensitive(it.input)
            if (it.expected != hadFlagSensitive) {
                throw Exception(
                    "For test '${it.desc}', " +
                        "expected ${it.expected}, " +
                        "but got $hadFlagSensitive."
                )
            }
        }
    }

    @Test
    fun testMatch() {
        data class TestCase(val desc: String, val input: String, val token: List<String>, val expected: Node?)
        val testCases = listOf<TestCase>(
            TestCase("Empty", "", listOf(""), null),
            TestCase("NonSensitiveMatch", "Quick", listOf("Quick"), null), // Token("Quick", false)
            TestCase("NonSensitiveNonMatch", "Quick", listOf("192.165.1.107"), null),
            TestCase("SensitiveNonMatch", "Quick", listOf("<s>192.165.1.107</s>"), null),
            TestCase("SensitiveMatch", "192.165.1.107", listOf("<s>192.165.1.107</s>"), null), // Token("192.165.1.107", true)
            TestCase("ManyNonSensitiveMatch", "Quit", listOf("Quick", "sort"), null), // Token( - , false)
            TestCase("ManyNonSensitivNonMatch", "Merge", listOf("Quick", "sort"), null),
            TestCase("ManySensitiveMatch", "134.785.2.137", listOf("<s>192.165.1.107</s>", "<s>345.789.2.235</s>"), null), // Token( - , true)
            TestCase("ManySensitiveNonMatch", "678.165.2.236", listOf("<s>192.165.1.107</s>", "<s>345.789.2.235</s>"), null)
        )
        testCases.forEach {
            val terminalTestCase = Terminal()
            it.token.forEach { terminalTestCase.addToken(it) }

            val tokenResult = terminalTestCase.match(it.input)

            if (it.expected != tokenResult) {
                throw Exception(
                    "For test '${it.desc}', " +
                        "expected ${it.expected}, " +
                        "but got $tokenResult."
                )
            }
        }
    }

    @Test
    fun testPrint() {
        // TODO: add test cases for print() method
    }
}
