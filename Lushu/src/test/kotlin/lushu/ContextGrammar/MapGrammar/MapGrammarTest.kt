package lushu.ContextGrammar.MapGrammar

import org.junit.jupiter.api.Test

class MapGrammarTest {
    @Test
    fun testConsume() {
        data class TestCase(
            val desc: String,
            val input: String,
            val expectedNumBlocks: Int
        )
        val testCases = listOf<TestCase>(
            TestCase(
                "Empty input",
                "",
                0
            ),
            TestCase(
                "One nested block",
                """
int main() {
    if (1 > 0) {
        printf("1 > 0\n");
    }
}
""",
                1
            ),
            TestCase(
                "Two nested blocks",
                """
int main() {
    if (1 > 0) {
        printf("1 > 0\n");
    }
    if (1 > 0) {
        printf("1 > 0\n");
    }
}
""",
                2
            ),
            TestCase(
                "Five nested blocks",
                """
int main() {
    if (1 > 0) {
        printf("1 > 0\n");
        if (1 > 0) {
            printf("1 > 0\n");
            if (1 > 0) {
                printf("1 > 0\n");
                if (1 > 0) {
                    printf("1 > 0\n");
                }
            }
        }
    }
    if (1 > 0) {
        printf("1 > 0\n");
    }
}
""",
                5
            )
        )
        testCases.forEach {
            println("Starting test ${it.desc}")
            val grammar = MapGrammar()
            grammar.addContext("<c>{ <*>{</*> }</c>")
            grammar.consume(it.input)
            val actual = grammar.getMaxBlocks()
            if (it.expectedNumBlocks != actual) {
                throw Exception(
                    "For test '${it.desc}', expected ${it.expectedNumBlocks}, " +
                        "but got $actual"
                )
            }
        }
    }
}
