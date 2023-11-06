package lushu.ContextGrammar.MapGrammar

import org.junit.jupiter.api.Test

class DSLTest {
    @Test
    fun testOpeningTags2ClosingTags() {
        data class TestCase(
            val desc: String,
            val openingTags: List<String>,
            val expectedClosingTags: List<String>
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "Context tag",
                listOf("<c>", "<a>"),
                listOf("</c>", "</a>")
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val dsl = DSL()
            val actual = dsl.openingTags2ClosingTags(it.openingTags)
            if (it.expectedClosingTags != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expectedClosingTags}, " +
                        "but got $actual"
                )
            }
        }
    }
}
