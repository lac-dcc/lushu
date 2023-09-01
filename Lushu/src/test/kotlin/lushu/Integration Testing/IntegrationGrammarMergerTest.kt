package lushu.ContextGrammar.Grammar

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
                "\n"
            ),
            TestCase(
                "a single context",
                listOf("<c>this is a context</c>"),
                "\n-this\n--is\n---a\n----\ncontext\n"
            ),
            TestCase(
                "non-mergeable node",
                listOf("<c>this is a <t>nonmergeable</t> node</c>", "<c>this is a mergeable node</c>"),
                "\n-this\n--is\n---a\n----\nnonmergeable\n-----node\n----mergeable\n-----node\n"
            ),
            TestCase(
                "equal",
                listOf("<c>this is a context</c>", "<c>this is a context</c>"),
                "\n-this\n--is\n---a\n----\ncontext\n"
            ),
            TestCase(
                "non-equal",
                listOf("<c>this is a context</c>", "<c>host ip: 123.456.798</c>"),
                "\n-this\n--is\n---a\n----\ncontext\n"
            ),
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val rule = Rules()
            it.contexts.forEach{ context ->
                rule.addContextsFromWords(context)
            }

            val actual = rules.contextToString()
            if (it.expected != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $actual"
                )
            }
        }
    }
}
