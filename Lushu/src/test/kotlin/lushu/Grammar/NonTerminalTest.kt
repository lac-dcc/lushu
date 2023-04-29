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
                "R0 :: [a]{1,1}\n"
            ),
            TestCase(
                "one word two letters",
                listOf<String>("ab"),
                "R0 :: [ab]{2,2}\n"
            ),
            TestCase(
                "ip",
                listOf<String>("1.2.3.4"),
                "R0 :: [1]{1,1}[.]{1,1}[2]{1,1}[.]{1,1}[3]{1,1}[.]{1,1}[4]{1,1}\n"
            ),
            TestCase(
                "two ips",
                listOf<String>("1.2.3.4", "4.3.2.1"),
                "R0 :: [1]{1,1}[.]{1,1}[2]{1,1}[.]{1,1}[3]{1,1}[.]{1,1}[4]{1,1} | R1\n" +
                "R1 :: [4]{1,1}[.]{1,1}[3]{1,1}[.]{1,1}[2]{1,1}[.]{1,1}[1]{1,1}\n"
            ),
            TestCase(
                "three words",
                listOf<String>("a", "b", "c"),
                "R0 :: [a]{1,1} | R1\n" +
                "R1 :: [b]{1,1} | R2\n" +
                "R2 :: [c]{1,1}\n"
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

    @Test
    fun testConsumeMultiple() {
        data class TestCase(
            val desc: String,

            // each line is a list of words
            val lines: List<List<String>>,

            val expected: String
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "two lines one word",
                listOf<List<String>>(listOf("a"),
                                     listOf("b")),
                "R0 :: [ab]{1,1}\n"
            ),
            TestCase(
                "two lines one words ip",
                listOf<List<String>>(listOf("1.2.3.4"),
                                     listOf("4.3.2.1")),
                "R0 :: [14]{1,1}[.]{1,1}[23]{1,1}[.]{1,1}[23]{1,1}[.]{1,1}[14]{1,1}\n"
            ),
            TestCase(
                "two lines two words received ip",
                listOf<List<String>>(listOf("received", "1.2.3.4"),
                                     listOf("received", "4.3.2.1")),
                "R0 :: [cdeirv]{8,8} | R1\n" +
                "R1 :: [14]{1,1}[.]{1,1}[23]{1,1}[.]{1,1}[23]{1,1}[.]{1,1}[14]{1,1}\n"
            ),
            TestCase(
                "two lines two words received got ip",
                listOf<List<String>>(listOf("received", "1.2.3.4"),
                                     listOf("got", "4.3.2.1")),
                "R0 :: [cdegiortv]{3,8} | R1\n" +
                "R1 :: [14]{1,1}[.]{1,1}[23]{1,1}[.]{1,1}[23]{1,1}[.]{1,1}[14]{1,1}\n"
            ),
            TestCase(
                "two lines multiple words equal",
                listOf<List<String>>(listOf("2023-04-29", "01:23:45,678", "received", "1.2.3.4"),
                                     listOf("2023-04-29", "01:23:45,678", "received", "1.2.3.4")),
                "R0 :: [023]{4,4}[-]{1,1}[04]{2,2}[-]{1,1}[29]{2,2} | R1\n" +
                "R1 :: [01]{2,2}[:]{1,1}[23]{2,2}[:]{1,1}[45]{2,2}[,]{1,1}[678]{3,3} | R2\n" +
                "R2 :: [cdeirv]{8,8} | R3\n" +
                "R3 :: [1]{1,1}[.]{1,1}[2]{1,1}[.]{1,1}[3]{1,1}[.]{1,1}[4]{1,1}\n"
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val rule = NonTerminal.new(it.lines[0][0])
            it.lines.forEach {
                rule.consume(it)
            }
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
