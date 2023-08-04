package lushu.ContextGrammar.Grammar

import lushu.Test.Utils.Utils
import org.junit.jupiter.api.Test

class GrammarTest{

    @Test
    fun testConsume() {

        data class TestCase(
            val desc: String,
            val input: MutableList<String>,
            val expected: String
        )

        val testCases = listOf<TestCase>(
            TestCase(
            "empty list",
            mutableListOf(),
            ""
            ),
            TestCase(
            "one word",
            mutableListOf("word"),
            "word"
            ),
            TestCase(
            "two words",
            mutableListOf("word", "word2"),
            "word word2"
            ),
            TestCase(
            "a phrase",
            mutableListOf("Testing", "the", "consume", "function", "for", "a", "large", "input"),
            "Testing the consume function for a large input"
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val grammar = Grammar()
            val res = grammar.consume(it.input)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

}