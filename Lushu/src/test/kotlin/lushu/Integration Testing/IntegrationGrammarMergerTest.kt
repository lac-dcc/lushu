package lushu.ContextGrammar.Grammar

import lushu.ContextGrammar.Grammar.Rules
import lushu.Test.Utils.Utils
import org.junit.jupiter.api.Test

class IntegrationGrammarMergerTest{

    @Test
    fun contextMergerTest(){
        data class TestCase(
            val desc: String,
            val contexts: List<String>,
            val expected: String
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "empty context",
                listOf(),
                "[]\n"
            ),
            TestCase(
                "a single context",
                listOf("<c>this is a context</c>"),
                "[]\n" +
                "-[IntervalNode(charset=Charset([t, h, i, s]) interval=Interval(4, 4) sensitive=false)]\n" +
                "--[IntervalNode(charset=Charset([i, s]) interval=Interval(2, 2) sensitive=false)]\n" + 
                "---[IntervalNode(charset=Charset([a]) interval=Interval(1, 1) sensitive=false)]\n" +
                "----[IntervalNode(charset=Charset([c, o, n, t, e, x]) interval=Interval(7, 7) sensitive=false)]\n"   
            ),
            TestCase(
                "ip's test",
                listOf("<c>192.68.255.255</c>", "<c>123.456.798.108</c>"),
                "[]\n" +
                "-[IntervalNode(charset=Charset([1, 9, 2, 3]) interval=Interval(3, 3) sensitive=false), IntervalNode(charset=Charset([.]) interval=Interval(1, 1) sensitive=false), IntervalNode(charset=Charset([6, 8, 4, 5]) interval=Interval(2, 3) sensitive=false), IntervalNode(charset=Charset([.]) interval=Interval(1, 1) sensitive=false), IntervalNode(charset=Charset([2, 5, 7, 9, 8]) interval=Interval(3, 3) sensitive=false), IntervalNode(charset=Charset([.]) interval=Interval(1, 1) sensitive=false), IntervalNode(charset=Charset([2, 5, 1, 0, 8]) interval=Interval(3, 3) sensitive=false)]\n"
            ),
            TestCase(
                "non-mergeable node",
                listOf("<c>this is a <t>nonmergeable</t> node</c>", "<c>this is a mergeable node</c>"),
                "[]\n" +
                "-[IntervalNode(charset=Charset([t, h, i, s]) interval=Interval(4, 4) sensitive=false)]\n" +
                "--[IntervalNode(charset=Charset([i, s]) interval=Interval(2, 2) sensitive=false)]\n" + 
                "---[IntervalNode(charset=Charset([a]) interval=Interval(1, 1) sensitive=false)]\n" +
                "----[IntervalNode(charset=Charset([n, o, m, e, r, g, a, b, l]) interval=Interval(12, 12) sensitive=false)]\n" +
                "-----[IntervalNode(charset=Charset([n, o, d, e]) interval=Interval(4, 4) sensitive=false)]\n" +
                "----[IntervalNode(charset=Charset([m, e, r, g, a, b, l]) interval=Interval(9, 9) sensitive=false)]\n" +
                "-----[IntervalNode(charset=Charset([n, o, d, e]) interval=Interval(4, 4) sensitive=false)]\n"
            ),
            TestCase(
                "non-equal",
                listOf("<c>this is a context</c>", "<c>host ip: 123.456.798</c>"),
                "[]\n" +
                "-[IntervalNode(charset=Charset([t, h, i, s, o]) interval=Interval(4, 4) sensitive=false)]\n" +
                "--[IntervalNode(charset=Charset([i, s]) interval=Interval(2, 2) sensitive=false)]\n" +
                "---[IntervalNode(charset=Charset([a]) interval=Interval(1, 1) sensitive=false)]\n" +
                "----[IntervalNode(charset=Charset([c, o, n, t, e, x]) interval=Interval(7, 7) sensitive=false)]\n" +
                "--[IntervalNode(charset=Charset([i, p]) interval=Interval(2, 2) sensitive=false), IntervalNode(charset=Charset([:]) interval=Interval(1, 1) sensitive=false)]\n" +
                "---[IntervalNode(charset=Charset([1, 2, 3]) interval=Interval(3, 3) sensitive=false), IntervalNode(charset=Charset([.]) interval=Interval(1, 1) sensitive=false), IntervalNode(charset=Charset([4, 5, 6]) interval=Interval(3, 3) sensitive=false), IntervalNode(charset=Charset([.]) interval=Interval(1, 1) sensitive=false),  IntervalNode(charset=Charset([7, 9, 8]) interval=Interval(3, 3) sensitive=false)]\n"
            ),
            TestCase(
                "equal",
                listOf("<c>this is a context</c>", "<c>this is a context</c>"),
                "\n-this\n--is\n---a\n----\ncontext\n"
            ),
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val rule = Rules()
            it.contexts.forEach{ context ->
                rule.addContextsFromWords(context)
            }

            val actual = rule.contextToString()
            if (it.expected != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }
}
